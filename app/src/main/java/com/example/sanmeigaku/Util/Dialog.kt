package com.example.sanmeigaku.Util

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.sanmeigaku.MainActivity
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