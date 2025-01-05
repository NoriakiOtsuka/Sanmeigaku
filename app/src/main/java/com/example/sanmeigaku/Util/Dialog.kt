package com.example.sanmeigaku.Util

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.sanmeigaku.MainActivity
import com.example.sanmeigaku.databinding.RegistrantDialogBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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

    /**
     * Create date picker dialog
     */
    @SuppressLint("Range")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = MainActivity
        val calendar = Calendar.getInstance()
        val year = if (activity.mDateExist) activity.mYear else calendar.get(Calendar.YEAR)
        val month = if (activity.mDateExist) activity.mMonth.minus(1) else calendar.get(Calendar.MONTH)
        val dayOfMonth = if (activity.mDateExist) activity.mDay else calendar.get(Calendar.DAY_OF_MONTH)
        val startDate = MainActivity.startDate
        val endDate = MainActivity.endDate

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

    /** VArray with registrant information */
    private lateinit var mRegistrantArray: Array<String>

    companion object {
        /** Instance of registrant dialog */
        fun newInstance(registrantArray: Array<String>): RegistrantDialog {
            val fragment = RegistrantDialog()
            val args = Bundle()
            args.putStringArray("registrantArray", registrantArray)
            fragment.arguments = args

            return fragment
        }
    }

    /**
     * Create registrant dialog
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mRegistrantArray = it.getStringArray("registrantArray") as Array<String>
        }
    }

    /**
     * Create registrant dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = RegistrantDialogBinding.inflate(layoutInflater)

        val title = getString(com.example.sanmeigaku.R.string.dialog_registrant_title)
        val message = getString(com.example.sanmeigaku.R.string.dialog_registrant_message)
        val okLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_ok)
        val ngLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_ng)
        val ntLabel = getString(com.example.sanmeigaku.R.string.dialog_registrant_label_nt)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { dialog, which ->
            }
            .setNegativeButton(ngLabel) { dialog, which ->
            }
            .setNeutralButton(ntLabel) { dialog, which ->
            }

        showRegistrantInfo()

        return builder.create()
    }

    /**
     * Destroy registrant dialog view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Show saved registration information in dialog
     */
    private fun showRegistrantInfo() {
        val name = mRegistrantArray[0]
        val kana = mRegistrantArray[1]
        val birthday =
            "${mRegistrantArray[2].substring(0, 4)}/" +
                    "${mRegistrantArray[2].substring(4, 6)}/" +
                    "${mRegistrantArray[2].substring(6, 8)}"
        val gender = mRegistrantArray[3].toInt()
        binding.clientInfoInputForm.nameEdit.setText(name)
        binding.clientInfoInputForm.kanaEdit.setText(kana)
        binding.clientInfoInputForm.birthdayEdit.setText(birthday)
        when (gender) {
            1 -> binding.clientInfoInputForm.genderMaleButton.isChecked = true
            2 -> binding.clientInfoInputForm.genderFemaleButton.isChecked = true
        }
    }
}