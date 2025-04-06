package com.example.sanmeigaku.Util

import android.app.AlertDialog
import android.content.Context
import android.text.InputFilter
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.sanmeigaku.MainActivity
import com.example.sanmeigaku.R
import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ClientInfoInput {
    private val TAG: String = "ClientInfoInput"

    /** Variable for filtering characters that can be entered when inputting kana */
    val kanaInputFilter =
        InputFilter { source, start, end, dest, dstart, dend ->
            val filter = source.toString().matches("^[a-zA-Z0-9 \u30A0-\u30FF　]++\$".toRegex())
            if (filter) {
                source
            } else {
                ""
            }
        }

    /** Variable for filtering characters that can be entered when inputting birthday */
    val birthdayInputFilter =
        InputFilter { source, start, end, dest, dstart, dend ->
            val filter = source.toString().matches("^[0-9/]++\$".toRegex())
            if (filter) {
                source
            } else {
                ""
            }
        }

    /**
     * Check if the date exists in the calendar
     */
    fun checkDateExist(strDate: String): Boolean {
        val format = DateFormat.getDateInstance()
        format.isLenient = false

        return try {
            format.parse(strDate)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Check if the date entered is within the range
     */
    fun checkDateSelectRange(context: Context, year: Int, month: Int, day: Int): Boolean {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.JAPAN)
        val sharedPref = context.getSharedPreferences("app_database", Context.MODE_PRIVATE)
        val startDate = sharedPref.getInt("start_date", 0)
        val endDate = sharedPref.getInt("end_date", 0)

        calendar.time = dateFormat.parse(startDate.toString()) as Date
        val start = calendar.timeInMillis

        calendar.time = dateFormat.parse(endDate.toString()) as Date
        val end = calendar.timeInMillis

        calendar.time = dateFormat.parse("%04d".format(year) + "%02d".format(month) + "%02d".format(day)) as Date
        val target = calendar.timeInMillis

        Log.i(TAG, "checkDateSelectRange: Range from $start to $end, with $target selected")

        return (target >= start) && (target <= end)
    }

    /**
     * Dialog when the client information entry form is not filled out
     */
    fun inputformNotFilledAlertDialog(context: Context, fragmentManager: FragmentManager, name: String, kana: String, dateExist: Boolean, gender: Int) {
        val title = context.getString(R.string.dialog_title_caution)
        var message = ""
        if (name == "")
            message += "${context.getString(R.string.common_name_title_text)} "
        if (kana == "")
            message += "${context.getString(R.string.common_kana_title_text)} "
        if (!dateExist)
            message += "${context.getString(R.string.common_birthday_title_text)} "
        if (gender == 0)
            message += "${context.getString(R.string.common_gender_title_text)} "
        message += context.getString(R.string.dialog_input_form_not_filled_in_message)

        val dialog = BaseDialog()
        dialog.simpleAlertDialog(context, fragmentManager, title, message)
    }

    /**
     * Dialog when date of birth is out of selection
     */
    fun inputDateRangeAlertDialog(context: Context, fragmentManager: FragmentManager) {
        val activity = MainActivity
        val startDateText = "${activity.mStartDate.toString().substring(0, 4)}/${activity.mStartDate.toString().substring(4, 6)}/${activity.mStartDate.toString().substring(6, 8)}"
        val endDateText = "${activity.mEndDate.toString().substring(0, 4)}/${activity.mEndDate.toString().substring(4, 6)}/${activity.mEndDate.toString().substring(6, 8)}"

        val dialog = BaseDialog()
        val title = context.getString(R.string.dialog_title_caution)
        val message = "${context.getString(R.string.dialog_failed_input_date_range_message)}\n $startDateText ～ $endDateText"
        dialog.simpleAlertDialog(context, fragmentManager, title, message)
    }

    /**
     * Dialog when dialog creation failed
     */
    fun creationFailedAlertDialog(context: Context, message: String): AlertDialog {
        val title = context.getString(R.string.dialog_title_caution)
        val okLabel = context.getString(R.string.dialog_label_ok)
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(okLabel) { _, _ ->
            }
            .show()

        return dialog
    }
}