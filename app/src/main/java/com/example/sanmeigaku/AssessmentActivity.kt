package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sanmeigaku.databinding.ActivityAssessmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.Period

class AssessmentActivity : AppCompatActivity() {
    private val TAG: String = "AssessmentActivity"
    private lateinit var binding: ActivityAssessmentBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager2

    companion object {
        /** Variables of user info received from the main activity */
        var mName: String = ""
        var mYear: Int = 0
        var mMonth: Int = 0
        var mDay: Int = 0
        var mGender: Int = 0
        var mAge: Int = 0
    }

    /**
     * Create assessment activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssessmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(TAG, "onCreate: create assessment activity")

        pagerAdapter = PagerAdapter(this)
        viewPager = binding.viewPager
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, position ->
            Log.i(TAG, "onCreate: tab position is $position")

            when (position) {
                0 -> tab.text = getString(R.string.tab_meishiki)
            }
        }.attach()

        mName = intent.getStringExtra("name").toString()
        mYear = intent.getIntExtra("year", 0)
        mMonth = intent.getIntExtra("month", 0)
        mDay = intent.getIntExtra("day", 0)
        mGender = intent.getIntExtra("gender", 0)
        mAge = setAge()
    }

    /**
     * Destroy assessment activity
     */
    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: destroy assessment activity")
    }

    /**
     * Calculate and set the age from the birthday
     */
    private fun setAge(): Int {
        val birthday = "%04d".format(mYear) + "-" +  "%02d".format(mMonth) + "-"  + "%02d".format(mDay)
        val today = LocalDate.now()

        return Period.between(LocalDate.parse(birthday), today).years
    }
}

/**
 * Page adapter to switch between assessment
 */
private class PagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val TAG: String = "PagerAdapter"

    /**
     * Manage number of tabs
     */
    override fun getItemCount(): Int = 1

    /**
     * Create a fragment of the selected tab
     */
    override fun createFragment(position: Int): Fragment {
        Log.i(TAG, "createFragment: fragment tab position is $position")

        return when (position) {
            0 -> MeishikiFragment()
            else -> MeishikiFragment()
        }
    }
}