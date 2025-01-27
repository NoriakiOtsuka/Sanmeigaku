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
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.MainActivity
import com.example.sanmeigaku.RegistrantActivity
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
    private lateinit var fragmentManager: FragmentManager
    private lateinit var mAppDBHelper: AppDBHelpler
    private val mDialog: BaseDialog = BaseDialog()
    private val mClientInfoInput: ClientInfoInput = ClientInfoInput()

    /** Array with registrant information */
    private var mPosition: Int = 0
    private lateinit var mRegistrantArray: ArrayList<String>
    private var mRegistrantId: Int = 0

    /** variable of name */
    private var mName: String = ""
    private var mKana: String = ""

    /** variable of birthday */
    private var mBirthday: Int = 0
    private var mDateExist: Boolean = true
    private var mDateFormat: Boolean = true
    private var mDateRange: Boolean = true

    /** variable of gender */
    private var mGender: Int = 0

    companion object {
        /** Instance of registrant dialog */
        fun newInstance(
            position: Int,
            registrantArray: MutableList<String>
        ): RegistrantDialog {
            val fragment = RegistrantDialog()
            val args = Bundle()
            args.putInt("position", position)
            args.putStringArrayList("registrantArray", ArrayList(registrantArray))
            fragment.arguments = args

            return fragment
        }
    }

    /**
     * Attach registrant dialog
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        fragmentManager = requireActivity().supportFragmentManager
        Log.i(TAG, "onAttach: registrant dialog attached")
    }

    /**
     * Detach registrant dialog
     */
    override fun onDetach() {
        super.onDetach()
        _binding = null
        Log.i(TAG, "onDetach: registrant dialog detached")
    }

    /**
     * Create registrant dialog
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mPosition = it.getInt("position")
            mRegistrantArray = it.getStringArrayList("registrantArray") as ArrayList<String>
        }
        Log.i(TAG, "onCreate: create registrant dialog")

        mAppDBHelper = AppDBHelpler(mContext)
        mAppDBHelper.writableDatabase
        mRegistrantId = mAppDBHelper.getRegistrantId(mRegistrantArray)

        mName = mRegistrantArray[0]
        mKana = mRegistrantArray[1]
        mBirthday = mRegistrantArray[2].toInt()
        mGender = mRegistrantArray[3].toInt()
    }

    /**
     * Create registrant dialog body
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = RegistrantDialogBinding.inflate(layoutInflater)
        Log.i(TAG, "onCreateDialog: create registrant dialog body")

        val builder = AlertDialog.Builder(requireActivity())
        var title = getString(com.example.sanmeigaku.R.string.dialog_registrant_title)
        var message = getString(com.example.sanmeigaku.R.string.dialog_registrant_message)
        val okLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_ok)
        val ngLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_ng)
        val ntLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_nt)
        val duration = Toast.LENGTH_SHORT
        builder.setView(binding.root)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { _, _ ->
                title = getString(com.example.sanmeigaku.R.string.dialog_caution_title)
                if ((mName != "") && (mKana != "") && (mDateExist) && (mGender > 0)) {
                    if (mDateFormat) {
                        if (mDateRange) {
                            mRegistrantArray[0] = mName
                            mRegistrantArray[1] = mKana
                            mRegistrantArray[2] = mBirthday.toString()
                            mRegistrantArray[3] = mGender.toString()

                            val checkDuplicate = mAppDBHelper.checkDuplicateRegistrant(mRegistrantId, mRegistrantArray)
                            when (checkDuplicate) {
                                0 -> {}
                                -1 -> {
                                    message = getString(com.example.sanmeigaku.R.string.dialog_failed_add_registrant_list_message_unique)
                                    mDialog.simpleAlertDialog(mContext, fragmentManager, title, message)
                                }
                                else -> {
                                    mAppDBHelper.updateRegistrant(mRegistrantId, mRegistrantArray)
                                    (activity as RegistrantActivity).registrantListAdapter.updateItem(mPosition, mRegistrantArray)

                                    message = getString(com.example.sanmeigaku.R.string.toast_succeeded_update_registrant_list_message)
                                    val toast = Toast.makeText(context, message, duration)
                                    toast.show()
                                }
                            }
                        } else {
                            mClientInfoInput.inputDateRangeAlertDialog(mContext, fragmentManager)
                        }
                    } else {
                        message = getString(com.example.sanmeigaku.R.string.dialog_failed_input_date_formant_message)
                        mDialog.simpleAlertDialog(mContext, fragmentManager, title, message)
                    }
                } else {
                    message = ""
                    if (mName == "")
                        message += "${getString(com.example.sanmeigaku.R.string.common_name_title_text)} "
                    if (mKana == "")
                        message += "${getString(com.example.sanmeigaku.R.string.common_kana_title_text)} "
                    if (!mDateExist)
                        message += "${getString(com.example.sanmeigaku.R.string.common_birthday_title_text)} "
                    if (mGender == 0)
                        message += "${getString(com.example.sanmeigaku.R.string.common_gender_title_text)} "
                    message += getString(com.example.sanmeigaku.R.string.dialog_input_form_not_filled_in_message)
                    mDialog.simpleAlertDialog(mContext, fragmentManager, title, message)
                }
            }
            .setNegativeButton(ngLabel) { _, _ ->
                mAppDBHelper.deleteRegistrant(mRegistrantId)
                (activity as RegistrantActivity).registrantListAdapter.deleteItem(mPosition)

                message = getString(com.example.sanmeigaku.R.string.toast_succeeded_delete_registrant_list_message)
                val toast = Toast.makeText(context, message, duration)
                toast.show()
            }
            .setNeutralButton(ntLabel) { dialog, _ ->
                dialog.cancel()
            }

        return builder.create()
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
     * Show saved registration information in dialog
     */
    private fun showRegistrantInfo() {
        val birthday = "${mBirthday.div(10000)}/" +
                "${mBirthday.div(100).mod(100)}/" +
                "${mBirthday.mod(100)}"
        binding.clientInfoInputForm.nameEdit.setText(mName)
        binding.clientInfoInputForm.kanaEdit.setText(mKana)
        binding.clientInfoInputForm.birthdayEdit.setText(birthday)
        when (mGender) {
            1 -> binding.clientInfoInputForm.genderMaleButton.isChecked = true
            2 -> binding.clientInfoInputForm.genderFemaleButton.isChecked = true
        }
    }

    /**
     * Show saved registration information in dialog
     */
    private fun editRegistrantInfo() {
        binding.clientInfoInputForm.nameEdit.doAfterTextChanged { name ->
            mName = name.toString()
            Log.i(TAG, "editRegistrantInfo: The name input in the edit text is $mName")
        }

        binding.clientInfoInputForm.kanaEdit.also {
            it.filters = arrayOf(mClientInfoInput.kanaInputFilter)
            it.doAfterTextChanged { kana ->
                mKana = kana.toString()
                Log.i(TAG, "editRegistrantInfo: The kana input in the edit text is $mKana")
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
                    mBirthday =
                        year.times(10000).plus(month.times(100)).plus(day)
                    mDateRange = mClientInfoInput.checkDateSelectRange(mContext, year, month, day)
                    Log.i(TAG, "editRegistrantInfo: The birthday input in the edit text is $mBirthday")
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
                            mClientInfoInput.inputDateRangeAlertDialog(mContext, fragmentManager)
                    } else {
                        val message = getString(com.example.sanmeigaku.R.string.dialog_failed_input_date_formant_message)
                        mDialog.simpleAlertDialog(mContext, fragmentManager, title, message)
                    }
                } else {
                    val message = getString(com.example.sanmeigaku.R.string.common_birthday_title_text) +
                            getString(com.example.sanmeigaku.R.string.dialog_input_form_not_filled_in_message)
                    mDialog.simpleAlertDialog(mContext, fragmentManager, title, message)
                }
            }
            return@setOnEditorActionListener false
        }

        binding.clientInfoInputForm.birthdayButton.setOnClickListener {
            val year = mBirthday.div(10000)
            val month = mBirthday.div(100).mod(100)
            val day = mBirthday.mod(100)
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
            dialog.show(fragmentManager, "")
        }

        binding.clientInfoInputForm.genderButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                com.example.sanmeigaku.R.id.genderMaleButton -> mGender = 1
                com.example.sanmeigaku.R.id.genderFemaleButton -> mGender = 2
            }
            Log.i(TAG, "editRegistrantInfo: The gender selected from radio button group is $mGender")
        }
    }
}