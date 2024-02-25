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

class MessageDialog(
    private val title: String,
    private val message: String,
    private val okLabel: String,
    private val okSelected: ()-> Unit,
    private val ngLabel: String,
    private val ngSelected: ()-> Unit
) : DialogFragment() {

    /**
     * Create simple alert dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { dialog, which ->
                okSelected()
            }
            .setNegativeButton(ngLabel) { dialog, which ->
                ngSelected()
            }

        return builder.create()
    }
}

class DateDialog(private val onSelected: (String) -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {
    private val TAG: String = "DateDialog"

    /**
     * Create date picker dialog
     */
    @SuppressLint("Range")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val startDate = MainActivity.startDate
        val endDate = MainActivity.endDate

        val datePicker = DatePickerDialog(requireActivity(), R.style.Theme_Holo_Dialog, this, year, month, day)
            .also {
                val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.JAPAN)
                calendar.time = dateFormat.parse(startDate.toString()) as Date
                it.datePicker.minDate = calendar.timeInMillis
                calendar.time = dateFormat.parse(endDate.toString()) as Date
                it.datePicker.maxDate = calendar.timeInMillis
            }
        Log.i(TAG, "onCreateDialog: A date picker dialog was created with dates from the start date (${startDate}) to the end date (${endDate}.")

        return datePicker
    }

    /**
     * Set selected date in picker dialog
     */
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        onSelected("$year" + "/" + "%02d".format(month + 1) + "/" + "%02d".format(dayOfMonth))
        Log.i(TAG, "onDateSet: The birthday selected in picker dialog has been set.")
    }
}