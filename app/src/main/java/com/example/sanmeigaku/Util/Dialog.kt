package com.example.sanmeigaku.Util

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.MainActivity
import com.example.sanmeigaku.MainApplication
import com.example.sanmeigaku.ViewModel.AssessmentViewModel
import com.example.sanmeigaku.ViewModel.RegistrantViewModel
import com.example.sanmeigaku.databinding.RegistrantDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class BaseDialog : DialogFragment() {
    /**
     * Alert dialog with simple OK button
     */
    fun simpleAlertDialog(context: Context, fragmentManager: FragmentManager, title: String, message: String) {
        val okLabbel = context.getString(com.example.sanmeigaku.R.string.dialog_message_label_ok)
        val dialog = MessageDialog.newInstance(title, message, okLabbel, "")
        dialog.isCancelable = false
        dialog.show(fragmentManager, "")
    }
}

class MessageDialog : DialogFragment() {
    private val TAG: String = "MessageDialog"

    companion object {
        /** Instance of message dialog */
        fun newInstance(title: String, message: String, okLabel: String, ngLabel: String): MessageDialog {
            val fragment = MessageDialog()
            val args = Bundle()
            args.putString("title", title)
            args.putString("message", message)
            args.putString("okLabel", okLabel)
            args.putString("ngLabel", ngLabel)
            fragment.arguments = args

            return fragment
        }
    }

    /**
     * Create simple alert dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments?.getString("title")
        val message = arguments?.getString("message")
        val okLabel = arguments?.getString("okLabel")
        val ngLabel = arguments?.getString("ngLabel")

        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { dialog, which ->
            }
            .setNegativeButton(ngLabel) { dialog, which ->
            }
        Log.i(TAG, "onCreateDialog: A message dialog was created")

        return builder.create()
    }
}

class DateSelectDialog() : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val TAG: String = "DateSelectDialog"
    private var listener: DatePickerListener? = null

    /**
     * Interface for listeners to detect that a date has been selected
     */
    interface DatePickerListener {
        fun onDateSelected(year: Int, month: Int, dayOfMonth: Int)
    }

    companion object {
        /** Instance of date select dialog */
        fun newInstance(year: Int, month: Int, day: Int):  DateSelectDialog {
            val fragment = DateSelectDialog()
            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", month)
            args.putInt("day", day)
            fragment.arguments = args

            return fragment
        }
    }

    /**
     * Create date picker dialog
     */
    @SuppressLint("Range")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = MainActivity
        val calendar = Calendar.getInstance()
        val year = arguments?.getInt("year") ?: calendar.get(Calendar.YEAR)
        val month = arguments?.getInt("month")?.minus(1) ?: calendar.get(Calendar.MONTH)
        val dayOfMonth = arguments?.getInt("day") ?: calendar.get(Calendar.DAY_OF_MONTH)

        val startDate = activity.mStartDate
        val endDate = activity.mEndDate

        val datePicker = DatePickerDialog(requireActivity(), R.style.Theme_Holo_Dialog, this, year, month, dayOfMonth)
            .also {
                val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.JAPAN)
                calendar.time = dateFormat.parse(startDate.toString()) as Date
                it.datePicker.minDate = calendar.timeInMillis
                calendar.time = dateFormat.parse(endDate.toString()) as Date
                it.datePicker.maxDate = calendar.timeInMillis
            }
        Log.i(TAG, "onCreateDialog: A date picker dialog was created with dates from the start date (${startDate}) to the end date (${endDate}")

        return datePicker
    }

    /**
     * Set selected date in picker dialog
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener?.onDateSelected(year, month.plus(1), dayOfMonth)
        Log.i(TAG, "onDateSet: The birthday selected in picker dialog has been set")
    }

    /**
     * Setter for listeners to detect that a date has been selected
     */
    fun setDatePickerListener(listener: DatePickerListener) {
        this.listener = listener
    }
}

class RegistrantDialog() : DialogFragment() {
    private val TAG: String = "RegistrantDialog"
    private var _binding: RegistrantDialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var mFragmentManager: FragmentManager
    private var mFragmentTag: String? = null
    private lateinit var mAppDBHelper: AppDBHelpler
    private val mDialog: BaseDialog = BaseDialog()
    private val mClientInfoInput: ClientInfoInput = ClientInfoInput()

    /** Variable of application */
    private lateinit var mApp: MainApplication

    /** View model for registrant */
    private lateinit var mAssessmentViewModel: AssessmentViewModel
    private val mRegistrantViewModel: RegistrantViewModel by activityViewModels()

    /** Variable of birthday */
    private var mDateExist: Boolean = true
    private var mDateFormat: Boolean = true
    private var mDateRange: Boolean = true

    /** A listener for handling events when client information is registered */
    private var clientInfoRegisteredListener: OnClientInfoRegisteredListener? = null

    /** Interface for a callback when client information is registered */
    interface OnClientInfoRegisteredListener {
        fun onClientInfoRegistered()
    }

    /**
     * Attach registrant dialog
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mFragmentManager = requireActivity().supportFragmentManager

        if (parentFragment is OnClientInfoRegisteredListener)
            clientInfoRegisteredListener = parentFragment as OnClientInfoRegisteredListener

        Log.i(TAG, "onAttach: registrant dialog attached")
    }

    /**
     * Detach registrant dialog
     */
    override fun onDetach() {
        super.onDetach()
        _binding = null
        clientInfoRegisteredListener = null
        Log.i(TAG, "onDetach: registrant dialog detached")
    }

    /**
     * Create registrant dialog
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create registrant dialog")

        mApp = requireActivity().application as MainApplication
        mAssessmentViewModel = ViewModelProvider(mApp).get(AssessmentViewModel::class.java)
        mAppDBHelper = AppDBHelpler(mContext)
        mAppDBHelper.writableDatabase
        val registrantId = mRegistrantViewModel.registrantId.value
        if (registrantId == 0)
            mRegistrantViewModel.setRegistrantId(mAppDBHelper.getRegistrantId(mRegistrantViewModel))
        mFragmentTag = tag
    }

    /**
     * Create registrant dialog body
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = RegistrantDialogBinding.inflate(layoutInflater)
        Log.i(TAG, "onCreateDialog: create registrant dialog body")

        val registrantDialog = when (mFragmentTag) {
            "AddRegistrantTag" -> registrantAddDialog()
            "UpdateRegistrantTag" -> registrantUpdateDialog()
            else -> {
                val message = getString(com.example.sanmeigaku.R.string.dialog_registrant_message_failed)
                mClientInfoInput.creationFailedAlertDialog(mContext, message)
            }
        }
        registrantDialog.setCanceledOnTouchOutside(false)

        return registrantDialog
    }

    /**
     * Create registrant dialog view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView: registrant dialog view create")

        showRegistrantInfo()
        editRegistrantInfo()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * Destroy registrant dialog view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: registrant dialog view destroyed")
    }

    /**
     * Create dialog to add registrant
     */
    private fun registrantAddDialog(): AlertDialog {
        binding.clientInfoInputForm.let {
            it.birthdayEdit.let { it ->
                it.isFocusable = false
                it.background = null
            }
            it.birthdayButton.visibility = View.GONE
        }
        binding.clientInfoInputForm.let {
            it.genderMaleButton.isEnabled = false
            it.genderFemaleButton.isEnabled = false
        }

        val builder = AlertDialog.Builder(requireActivity())
        var title = getString(com.example.sanmeigaku.R.string.dialog_registrant_title)
        var message = getString(com.example.sanmeigaku.R.string.dialog_registrant_message_add)
        val okLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_add)
        val ntLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_cancel)
        val duration = Toast.LENGTH_SHORT
        builder.setView(binding.root)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { _, _ ->
                val name = mAssessmentViewModel.name.value!!
                val kana = mAssessmentViewModel.kana.value!!
                val gender = mAssessmentViewModel.gender.value!!
                title = getString(com.example.sanmeigaku.R.string.dialog_caution_title)
                if ((name != "") && (kana != "")) {
                    val result = mAppDBHelper.addRegistrant(mAssessmentViewModel)
                    when (result) {
                        1 -> {
                            clientInfoRegisteredListener?.onClientInfoRegistered()
                            message = getString(com.example.sanmeigaku.R.string.toast_succeeded_add_registrant_list_message)
                            val toast = Toast.makeText(mContext, message, duration)
                            toast.show()
                        }
                        -1 -> {
                            message = getString(com.example.sanmeigaku.R.string.dialog_failed_add_registrant_list_message_unique)
                            mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                        }
                        else -> {
                            message = getString(com.example.sanmeigaku.R.string.dialog_failed_add_registrant_list_message)
                            mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                        }
                    }
                } else {
                    mClientInfoInput.inputformNotFilledAlertDialog(mContext, mFragmentManager, name, kana, mDateExist, gender)
                }
                clearRegistrantInfo()
            }
            .setNeutralButton(ntLabel) { dialog, _ ->
                clearRegistrantInfo()
                dialog.cancel()
            }

        return builder.create()
    }

    /**
     * Create dialog to update registrant info
     */
    private fun registrantUpdateDialog(): AlertDialog {
        val builder = AlertDialog.Builder(requireActivity())
        var title = getString(com.example.sanmeigaku.R.string.dialog_registrant_title)
        var message = getString(com.example.sanmeigaku.R.string.dialog_registrant_message_update_delete)
        val okLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_update)
        val ngLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_delete)
        val ntLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_cancel)
        val position = mRegistrantViewModel.itemPosition.value!!
        val duration = Toast.LENGTH_SHORT
        builder.setView(binding.root)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { _, _ ->
                val name = mRegistrantViewModel.name.value!!
                val kana = mRegistrantViewModel.kana.value!!
                val birthday = mRegistrantViewModel.birthday.value.toString()
                val gender = mRegistrantViewModel.gender.value!!
                title = getString(com.example.sanmeigaku.R.string.dialog_caution_title)
                if ((name != "") && (kana != "") && (mDateExist) && (gender > 0)) {
                    if (mDateFormat) {
                        if (mDateRange) {
                            val registrantArray = arrayListOf(name, kana, birthday, gender.toString())
                            val result = mAppDBHelper.checkDuplicateRegistrant(mRegistrantViewModel)
                            when (result) {
                                in 1..Int.MAX_VALUE -> {
                                    mAppDBHelper.updateRegistrant(mRegistrantViewModel)
                                    mRegistrantViewModel.updateItem(position, registrantArray)

                                    message = getString(com.example.sanmeigaku.R.string.toast_succeeded_update_registrant_list_message)
                                    val toast = Toast.makeText(context, message, duration)
                                    toast.show()
                                }
                                -1 -> {
                                    message = getString(com.example.sanmeigaku.R.string.dialog_failed_add_registrant_list_message_unique)
                                    mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                                }
                                else -> {}
                            }
                        } else {
                            mClientInfoInput.inputDateRangeAlertDialog(mContext, mFragmentManager)
                        }
                    } else {
                        message = getString(com.example.sanmeigaku.R.string.dialog_failed_input_date_formant_message)
                        mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                    }
                } else {
                    mClientInfoInput.inputformNotFilledAlertDialog(mContext, mFragmentManager, name, kana, mDateExist, gender)
                }
                clearRegistrantInfo()
            }
            .setNegativeButton(ngLabel) { _, _ ->
                mAppDBHelper.deleteRegistrant(mRegistrantViewModel)
                mRegistrantViewModel.deleteItem(position)
                clearRegistrantInfo()

                message = getString(com.example.sanmeigaku.R.string.toast_succeeded_delete_registrant_list_message)
                val toast = Toast.makeText(context, message, duration)
                toast.show()
            }
            .setNeutralButton(ntLabel) { dialog, _ ->
                clearRegistrantInfo()
                dialog.cancel()
            }

        return builder.create()
    }

    /**
     * Show saved registration information in dialog
     */
    private fun showRegistrantInfo() {
        val name = when (mFragmentTag) {
            "AddRegistrantTag" -> mAssessmentViewModel.name.value.toString()
            "UpdateRegistrantTag" -> mRegistrantViewModel.name.value.toString()
            else -> ""
        }

        val kana = when (mFragmentTag) {
            "AddRegistrantTag" -> mAssessmentViewModel.kana.value.toString()
            "UpdateRegistrantTag" -> mRegistrantViewModel.kana.value.toString()
            else -> ""
        }

        val birthday = when (mFragmentTag) {
            "AddRegistrantTag" -> {
                "${mAssessmentViewModel.year.value}/" +
                        "${mAssessmentViewModel.month.value}/" +
                        "${mAssessmentViewModel.day.value}"
            }
            "UpdateRegistrantTag" -> {
                when (val param = mRegistrantViewModel.birthday.value!!) {
                    0 -> ""
                    else -> {
                        "${param.div(10000)}/" +
                                "${param.div(100).mod(100)}/" +
                                "${param.mod(100)}"
                    }
                }
            }
            else -> ""
        }

        val gender = when (mFragmentTag) {
            "AddRegistrantTag" -> mAssessmentViewModel.gender.value
            "UpdateRegistrantTag" -> mRegistrantViewModel.gender.value
            else -> 0
        }

        binding.clientInfoInputForm.nameEdit.setText(name)
        binding.clientInfoInputForm.kanaEdit.setText(kana)
        binding.clientInfoInputForm.birthdayEdit.setText(birthday)
        when (gender) {
            1 -> binding.clientInfoInputForm.genderMaleButton.isChecked = true
            2 -> binding.clientInfoInputForm.genderFemaleButton.isChecked = true
        }
    }

    /**
     * Show saved registration information in dialog
     */
    private fun editRegistrantInfo() {
        binding.clientInfoInputForm.nameEdit.doAfterTextChanged { name ->
            when (mFragmentTag) {
                "AddRegistrantTag" -> mAssessmentViewModel.setName(name.toString())
                "UpdateRegistrantTag" -> mRegistrantViewModel.setName(name.toString())
            }
        }

        binding.clientInfoInputForm.kanaEdit.also {
            it.filters = arrayOf(mClientInfoInput.kanaInputFilter)
            it.doAfterTextChanged { kana ->
                when (mFragmentTag) {
                    "AddRegistrantTag" -> mAssessmentViewModel.setKana(kana.toString())
                    "UpdateRegistrantTag" -> mRegistrantViewModel.setKana(kana.toString())
                }
            }
        }

        binding.clientInfoInputForm.birthdayEdit.also {
            it.filters = arrayOf(mClientInfoInput.birthdayInputFilter)
            it.doAfterTextChanged { date ->
                val dateText = date.toString()
                mDateExist = dateText != ""

                mDateFormat = mClientInfoInput.checkDateExist(dateText)
                if (mDateFormat) {
                    val dateArray = dateText.split("/")
                    val year = dateArray[0].toInt()
                    val month = dateArray[1].toInt()
                    val day = dateArray[2].toInt()
                    mRegistrantViewModel.setBirthday(year.times(10000).plus(month.times(100)).plus(day))
                    mDateRange = mClientInfoInput.checkDateSelectRange(mContext, year, month, day)
                } else {
                    Log.i(TAG, "editRegistrantInfo: The birthday input in the edit text is not applied")
                }
            }
        }

        binding.clientInfoInputForm.birthdayEdit.setOnEditorActionListener() { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE) {
                val title = getString(com.example.sanmeigaku.R.string.dialog_caution_title)
                Log.i(TAG, "editRegistrantInfo: In birthday input field, enter key is tapped")
                if (mDateExist) {
                    if (mDateFormat) {
                        if (!mDateRange)
                            mClientInfoInput.inputDateRangeAlertDialog(mContext, mFragmentManager)
                    } else {
                        val message = getString(com.example.sanmeigaku.R.string.dialog_failed_input_date_formant_message)
                        mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                    }
                } else {
                    val message = getString(com.example.sanmeigaku.R.string.common_birthday_title_text) +
                            getString(com.example.sanmeigaku.R.string.dialog_input_form_not_filled_in_message)
                    mDialog.simpleAlertDialog(mContext, mFragmentManager, title, message)
                }
            }
            return@setOnEditorActionListener false
        }

        binding.clientInfoInputForm.birthdayButton.setOnClickListener {
            val birthday = mRegistrantViewModel.birthday.value!!
            val year = birthday.div(10000)
            val month = birthday.div(100).mod(100)
            val day = birthday.mod(100)
            val dialog = DateSelectDialog.newInstance(year, month, day)

            dialog.setDatePickerListener(object : DateSelectDialog.DatePickerListener {
                override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
                    val date = "$year/$month/$dayOfMonth"
                    binding.clientInfoInputForm.birthdayEdit.setText(date)
                    Log.i(TAG, "editRegistrantInfo: The birthday selected in date picker dialog is $date")
                    mDateFormat = true
                    mDateRange = true
                }
            })
            dialog.isCancelable = false
            dialog.show(mFragmentManager, "")
        }

        binding.clientInfoInputForm.genderButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                com.example.sanmeigaku.R.id.genderMaleButton -> mRegistrantViewModel.setGender(1)
                com.example.sanmeigaku.R.id.genderFemaleButton -> mRegistrantViewModel.setGender(2)
            }
        }
    }

    /**
     * Clear registration information in dialog
     */
    private fun clearRegistrantInfo() {
        mRegistrantViewModel.setRegistrantId(0)
        mRegistrantViewModel.setItemPosition(-1)
        mRegistrantViewModel.setName("")
        mRegistrantViewModel.setKana("")
        mRegistrantViewModel.setBirthday(0)
        mRegistrantViewModel.setGender(0)
    }
}