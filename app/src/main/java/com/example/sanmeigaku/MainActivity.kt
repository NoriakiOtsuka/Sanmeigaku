package com.example.sanmeigaku

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.DB.AssetsDBHelper
import com.example.sanmeigaku.Util.BaseDialog
import com.example.sanmeigaku.Util.ClientInfoInput
import com.example.sanmeigaku.Util.DateSelectDialog
import com.example.sanmeigaku.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val mDialog: BaseDialog = BaseDialog()
    private val mClientInfoInput: ClientInfoInput = ClientInfoInput()

    /** variable of name */
    private var mName: String = ""
    private var mKana: String = ""

    /** variable of birthday */
    private var mDateFormat: Boolean = false
    private var mDateRange: Boolean = false

    /** variable of gender */
    private var mGender: Int = 0

    companion object {
        /** variable of birthday */
        var mYear: Int = 0
        var mMonth: Int = 0
        var mDay: Int = 0
        var mDateExist: Boolean = false

        /** Variable of select range of date */
        var mStartDate = 0
        var mEndDate = 0
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
            binding.clientInfoInputForm.birthdayEdit.isEnabled = false
            binding.clientInfoInputForm.birthdayButton.isEnabled = false

            val title = getString(R.string.dialog_caution_title)
            val message = getString(R.string.dialog_failed_set_date_range_message)
            mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
        }

        binding.registeredButton.setOnClickListener {
            val intent = Intent(this, RegistrantActivity::class.java)
            startActivity(intent)
        }

        binding.clientInfoInputForm.nameEdit.doAfterTextChanged { name ->
            mName = name.toString()
            Log.i(TAG, "onCreate: The name input in the edit text is $mName")
        }

        binding.clientInfoInputForm.kanaEdit.also {
            it.filters = arrayOf(mClientInfoInput.kanaInputFilter)
            it.doAfterTextChanged { kana ->
                mKana = kana.toString()
                Log.i(TAG, "onCreate: The kana input in the edit text is $mKana")
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
                    mYear = dateArray[0].toInt()
                    mMonth = dateArray[1].toInt()
                    mDay = dateArray[2].toInt()
                    mDateRange = mClientInfoInput.checkDateSelectRange(this, mYear, mMonth, mDay)
                    Log.i(TAG, "onCreate: The birthday input in the edit text is ${mYear}/${mMonth}/${mDay}")
                } else {
                    Log.i(TAG, "onCreate: The birthday input in the edit text is not applied")
                }
            }
        }

        binding.clientInfoInputForm.birthdayEdit.setOnEditorActionListener() { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE) {
                val title = getString(R.string.dialog_caution_title)
                Log.i(TAG, "onCreate: In birthday input field, enter key is tapped")
                if (mDateExist) {
                    if (mDateFormat) {
                        if (!mDateRange)
                            mClientInfoInput.inputDateRangeAlertDialog(this, supportFragmentManager)
                    } else {
                        val message = getString(R.string.dialog_failed_input_date_formant_message)
                        mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                    }
                } else {
                    val message = getString(R.string.common_birthday_title_text) +
                            getString(R.string.dialog_input_form_not_filled_in_message)
                    mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                }
            }
            return@setOnEditorActionListener false
        }

        binding.clientInfoInputForm.birthdayButton.setOnClickListener {
            val dialog = DateSelectDialog.newInstance(mYear, mMonth, mDay)
            dialog.setDatePickerListener(object : DateSelectDialog.DatePickerListener {
                override fun onDateSelected(year: Int, month: Int, dayOfMonth: Int) {
                    val date = "$year/$month/$dayOfMonth"
                    binding.clientInfoInputForm.birthdayEdit.setText(date)
                    Log.i(TAG, "onCreate: The birthday selected in date picker dialog is $date")
                    mDateFormat = true
                    mDateRange = true
                }
            })
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "")
        }

        binding.clientInfoInputForm.genderButtonGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.genderMaleButton -> mGender = 1
                R.id.genderFemaleButton -> mGender = 2
            }
            Log.i(TAG, "onCreate: The gender selected from radio button group is $mGender")
        }

        binding.divineButton.setOnClickListener {
            val title = getString(R.string.dialog_caution_title)
            if ((mDateExist) && (mGender > 0)) {
                if (mDateFormat) {
                    if (mDateRange) {
                        val intent = Intent(this, AssessmentActivity::class.java)
                        intent.putExtra("name", mName)
                        intent.putExtra("kana", mKana)
                        intent.putExtra("year", mYear)
                        intent.putExtra("month", mMonth)
                        intent.putExtra("day", mDay)
                        intent.putExtra("gender", mGender)
                        startActivity(intent)
                    } else {
                        mClientInfoInput.inputDateRangeAlertDialog(this, supportFragmentManager)
                    }
                } else {
                    val message = getString(R.string.dialog_failed_input_date_formant_message)
                    mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                }
            } else {
                var message = ""
                if (!mDateExist)
                    message += "${getString(R.string.common_birthday_title_text)} "
                if (mGender == 0)
                    message += "${getString(R.string.common_gender_title_text)} "
                message += getString(R.string.dialog_input_form_not_filled_in_message)
                mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
            }
        }

        binding.saveButton.setOnClickListener {
            val title = getString(R.string.dialog_caution_title)
            if ((mName != "") && (mKana != "") && (mDateExist) && (mGender > 0)) {
                if (mDateFormat) {
                    if (mDateRange) {
                        val appDBHelper = AppDBHelpler(this)
                        appDBHelper.writableDatabase

                        val birthday = mYear.times(10000).plus(mMonth.times(100)).plus(mDay)
                        val result = appDBHelper.addRegistrant(mName, mKana, birthday, mGender)
                        when (result) {
                            1 -> {
                                val message = getString(R.string.toast_succeeded_add_registrant_list_message)
                                val duration = Toast.LENGTH_SHORT
                                val toast = Toast.makeText(this, message, duration)
                                toast.show()
                            }
                            -1 -> {
                                val message = getString(R.string.dialog_failed_add_registrant_list_message_unique)
                                mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                            }
                            else -> {
                                val message = getString(R.string.dialog_failed_add_registrant_list_message)
                                mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                            }
                        }
                    } else {
                        mClientInfoInput.inputDateRangeAlertDialog(this, supportFragmentManager)
                    }
                } else {
                    val message = getString(R.string.dialog_failed_input_date_formant_message)
                    mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
                }
            } else {
                var message = ""
                if (mName == "")
                    message += "${getString(R.string.common_name_title_text)} "
                if (mKana == "")
                    message += "${getString(R.string.common_kana_title_text)} "
                if (!mDateExist)
                    message += "${getString(R.string.common_birthday_title_text)} "
                if (mGender == 0)
                    message += "${getString(R.string.common_gender_title_text)} "
                message += getString(R.string.dialog_input_form_not_filled_in_message)
                mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
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
            mDialog.simpleAlertDialog(this, supportFragmentManager, title, message)
        }
    }

    /**
     * Set a range of dates that can be selected
     */
    private fun setDateSelectRange(): Boolean {
        val sharedPref = getSharedPreferences("app_database", Context.MODE_PRIVATE)
        mStartDate = sharedPref.getInt("start_date", 0)
        mEndDate = sharedPref.getInt("end_date", 0)
        Log.i(TAG, "setDateSelectRange: The range date is set from the start date(${mStartDate}) to the end date(${mEndDate})")

        return !((mStartDate == 0) || (mEndDate == 0))
    }
}