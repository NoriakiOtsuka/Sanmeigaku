package com.example.sanmeigaku

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.DB.AssetsDBHelper
import com.example.sanmeigaku.Util.DateDialog
import com.example.sanmeigaku.Util.MessageDialog
import com.example.sanmeigaku.databinding.ActivityMainBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    /** variable of name */
    private var mName: String = ""
    private var mKana: String = ""

    /** variable of birthday */
    private var mYear: Int? = null
    private var mMonth: Int? = null
    private var mDay: Int? = null
    private var mDateFormat: Boolean = false

    /** variable of gender */
    private var mGender: Int = 0

    /** Variable for filtering characters that can be entered when inputting Kana */
    private val mKanaInputFilter =
        InputFilter { source, start, end, dest, dstart, dend ->
            val filter = source.toString().matches("^[a-zA-Z0-9 \u30A0-\u30FF　]++\$".toRegex())
            if (filter) {
                source
            } else {
                ""
            }
        }

    companion object {
        /** Variable of select range of date */
        var startDate = 0
        var endDate = 0
    }

    /**
     * Create main activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: create main activity")

        checkDatabaseExist()

        if (!setDateSelectRange()) {
            binding.birthdayEdit.isEnabled = false
            binding.birthdayButton.isEnabled = false

            val title = getString(R.string.dialog_caution_title)
            val message = getString(R.string.dialog_failed_set_date_range_message)
            simpleAlertDialog(title, message)
        }

        binding.nameEdit.doAfterTextChanged { name ->
            mName = name.toString()
            Log.i(TAG, "onCreate: The name input in the edit text is ${mName}")
        }

        binding.kanaEdit.also {
            it.filters = arrayOf(mKanaInputFilter)
            it.doAfterTextChanged { kana ->
                mKana = kana.toString()
                Log.i(TAG, "onCreate: The kana input in the edit text is ${mKana}")
            }
        }

        binding.birthdayEdit.doAfterTextChanged { date ->
            var dateText = date.toString()
            var str = dateText.replace("-", "/")
            str = str.replace(".", "/")
            val regex = Regex("""(\d+)/(\d+)/(\d+)""")
            mDateFormat = regex.containsMatchIn(str)

            if (mDateFormat) {
                val format = DateFormat.getDateInstance()
                val date = format.parse(str)
                val simpleDateFormat = SimpleDateFormat("yyyyMMdd", Locale.JAPAN)
                dateText = simpleDateFormat.format(date)

                mYear = dateText.substring(0, 4).toInt()
                mMonth = dateText.substring(4, 6).toInt()
                mDay = dateText.substring(6, 8).toInt()
                Log.i(TAG, "onCreate: The birthday input in the edit text is ${mYear}/${mMonth}/${mDay}")

                if (!checkDateSelectRange()) {
                    mDateFormat = false
                    inputDateRangeAlertDialog()
                }
            } else {
                Log.i(TAG, "onCreate: The birthday input in the edit text is not applied")
            }
        }

        binding.birthdayEdit.setOnEditorActionListener() { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE) {
                Log.i(TAG, "onCreate: In birthday input field, enter key is tapped")
                if (mDateFormat) {
                    if (!checkDateSelectRange()) {
                        mDateFormat = false
                        inputDateRangeAlertDialog()
                    }
                } else {
                    val title = getString(R.string.dialog_caution_title)
                    val message = getString(R.string.dialog_failed_input_date_formant_message)
                    simpleAlertDialog(title, message)
                }
            }
            return@setOnEditorActionListener false
        }

        binding.birthdayButton.setOnClickListener {
            DateDialog { date ->
                binding.birthdayEdit.setText(date)
                mDateFormat = true
            }.show(supportFragmentManager, "date_dialog")
            Log.i(TAG, "onCreate: The birthday selected in date picker dialog is ${mYear}/${mMonth}/${mDay}")
        }

        binding.genderButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.genderMaleButton -> mGender = 1
                R.id.genderFemaleButton -> mGender = 2
            }
            Log.i(TAG, "onCreate: The gender selected from radio button group is ${mGender}")
        }

        binding.divineButton.setOnClickListener {
            if (mDateFormat && (mGender > 0)) {
                val intent = Intent(this, AssessmentActivity::class.java)
                intent.putExtra("name", mName)
                intent.putExtra("kana", mKana)
                intent.putExtra("year", mYear)
                intent.putExtra("month", mMonth)
                intent.putExtra("day", mDay)
                intent.putExtra("gender", mGender)
                startActivity(intent)
            }
        }

        binding.saveButton.setOnClickListener {
            val birthday = mYear?.times(10000)?.plus(mMonth!!.times(100))?.plus(mDay!!)
            if ((mName != "") && (mKana != "") &&(birthday != null) && (mGender > 0)) {
                if (mDateFormat) {
                    val appDBHelper = AppDBHelpler(this)
                    appDBHelper.writableDatabase

                    val result = appDBHelper.addRegistrant(mName, mKana, birthday, mGender)
                    val text = when (result) {
                        -1L -> getString(R.string.toast_failed_add_registrant_list_message)
                        else -> getString(R.string.toast_succeeded_add_registrant_list_message)
                    }
                    val duration = Toast.LENGTH_SHORT
                    val toast = Toast.makeText(this, text, duration)
                    toast.show()
                } else {
                    val title = getString(R.string.dialog_caution_title)
                    val message = getString(R.string.dialog_failed_input_date_formant_message)
                    simpleAlertDialog(title, message)
                }
            } else {
                val title = getString(R.string.dialog_caution_title)
                val message = getString(R.string.dialog_input_form_not_filled_in_message)
                simpleAlertDialog(title, message)
            }
        }
    }

    /**
     * Check if the database exists in assets
     */
    private fun checkDatabaseExist() {
        val assetsDbHelper = AssetsDBHelper(this)
        var assetDBExist = false
        val assets = resources.assets.list("")
        for (asset in assets!!) {
            if (asset.equals(assetsDbHelper.databaseName)) {
                Log.i(TAG, "checkDatabaseExist: database should be created or updated")
                val appDBHelper = AppDBHelpler(this)
                appDBHelper.writableDatabase
                assetsDbHelper.changeDatabase()
                appDBHelper.setDateRange(this)
                assetDBExist = true

                break
            }
        }

        if (!assetDBExist) {
            val title = getString(R.string.dialog_caution_title)
            val message = getString(R.string.dialog_failed_db_setup_message)
            simpleAlertDialog(title, message)
        }
    }

    /**
     * Set a range of dates that can be selected
     */
    private fun setDateSelectRange(): Boolean {
        val sharedPref = getSharedPreferences("app_database", Context.MODE_PRIVATE)
        startDate = sharedPref.getInt("start_date", 0)
        endDate = sharedPref.getInt("end_date", 0)
        Log.i(TAG, "setDateSelectRange: The range date is set from the start date(${startDate}) to the end date(${endDate})")

        return !((startDate == 0) || (endDate == 0))
    }

    /**
     * Check if the date entered is within the range
     */
    private fun checkDateSelectRange(): Boolean {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.JAPAN)

        calendar.time = dateFormat.parse(startDate.toString()) as Date
        val start = calendar.timeInMillis

        calendar.time = dateFormat.parse((endDate + 1).toString()) as Date
        val end = calendar.timeInMillis

        calendar.time = dateFormat.parse("%04d".format(mYear) + "%02d".format(mMonth) + "%02d".format(mDay)) as Date
        val target = calendar.timeInMillis

        Log.i(TAG, "checkDateSelectRange: Range from ${start} to ${end}, with ${target} selected")

        return (target > start) && (target < end)
    }

    /**
     * Alert dialog with simple OK button
     */
    private fun simpleAlertDialog(title: String, message: String) {
        val dialog = MessageDialog(title, message, "OK", {}, "", {})
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "")
    }

    /**
     * Dialog when date of birth is out of selection
     */
    private fun inputDateRangeAlertDialog() {
        val startDateText = "${startDate.toString().substring(0, 4)}/${startDate.toString().substring(4, 6)}/${startDate.toString().substring(6, 8)}"
        val endDateText = "${endDate.toString().substring(0, 4)}/${endDate.toString().substring(4, 6)}/${endDate.toString().substring(6, 8)}"

        val title = getString(R.string.dialog_caution_title)
        val message = "${getString(R.string.dialog_failed_input_date_range_message)}\n $startDateText ～ $endDateText"
        val dialog = MessageDialog(title, message, "OK", {}, "", {})
        dialog.isCancelable = false
        dialog.show(supportFragmentManager, "select_date_dialog")
    }
}