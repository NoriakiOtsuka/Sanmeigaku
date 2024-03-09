package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sanmeigaku.databinding.ActivityAssessmentBinding

class AssessmentActivity : AppCompatActivity() {
    private val TAG: String = "AssessmentActivity"
    private lateinit var binding: ActivityAssessmentBinding

    companion object {
        /** Variables of user info received from the main activity */
        var mName: String = ""
        var mYear: Int = 0
        var mMonth: Int = 0
        var mDay: Int = 0
        var mGender: Int = 0
    }

    /**
     * Create assessment activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mName = intent.getStringExtra("name").toString()
        mYear = intent.getIntExtra("year", 0)
        mMonth = intent.getIntExtra("month", 0)
        mDay = intent.getIntExtra("day", 0)
        mGender = intent.getIntExtra("gender", 0)
    }
}