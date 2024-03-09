package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sanmeigaku.databinding.ActivityAssessmentBinding

class AssessmentActivity : AppCompatActivity() {
    private val TAG: String = "AssessmentActivity"
    private lateinit var binding: ActivityAssessmentBinding

    /**
     * Create assessment activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}