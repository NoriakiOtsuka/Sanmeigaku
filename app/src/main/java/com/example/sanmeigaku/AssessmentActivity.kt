package com.example.sanmeigaku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.sanmeigaku.DB.AppDBHelpler
import com.example.sanmeigaku.databinding.ActivityAssessmentBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.roundToInt

class AssessmentActivity : AppCompatActivity() {
    private val TAG: String = "AssessmentActivity"
    private lateinit var binding: ActivityAssessmentBinding
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var mAppDBHelper: AppDBHelpler

    /** Variables related to the first day of the month in 24 Solar Terms */
    private var mFirstDay: Int = 0
    private var mFirstDayKanShiNo: Int = 0

    /** Variable of the difference from the end of the month to the birthday */
    var mDiffLastDay: Int = -1

    companion object {
        /** Variables of user info received from the main activity */
        var mName: String = ""
        var mYear: Int = 0
        var mMonth: Int = 0
        var mDay: Int = 0
        var mGender: Int = 0
        var mAge: Int = 0

        /** Variables of kan-shi number */
        var mYearKanShiNo: Int = 0
        var mMonthKanShiNo: Int = 0
        var mDayKanShiNo: Int = 0
        var mYearKanNo: Int = 0
        var mYearShiNo: Int = 0
        var mMonthKanNo: Int = 0
        var mMonthShiNo: Int = 0
        var mDayKanNo: Int = 0
        var mDayShiNo: Int = 0

        /** Variable of the difference from the beginning of the month to the birthday */
        var mDiffFirstDay: Int = 0
        var mFatalOrder: Int = 0
        var mTaiStartAge: Int = -1
        var mTaiKanNo: Int = 0
        var mTaiShiNo: Int = 0
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

        mAppDBHelper = AppDBHelpler(this)
        mAppDBHelper.writableDatabase

        val kanshiData = mAppDBHelper.readKanshiTable(mYear, mMonth, mDay)
        mFirstDay = kanshiData.date
        mYearKanShiNo = kanshiData.yearKanShi
        mMonthKanShiNo = kanshiData.monthKanShi
        mFirstDayKanShiNo = kanshiData.dateKanShi

        mDiffFirstDay = setDiffFirstDay()
        mDayKanShiNo = mFirstDayKanShiNo.plus(mDiffFirstDay - 1).rem(60) + 1

        mYearKanNo = mYearKanShiNo.minus(1).rem(10) + 1
        mYearShiNo = mYearKanShiNo.minus(1).rem(12) + 1
        mMonthKanNo = mMonthKanShiNo.minus(1).rem(10) + 1
        mMonthShiNo = mMonthKanShiNo.minus(1).rem(12) + 1
        mDayKanNo = mDayKanShiNo.minus(1).rem(10) + 1
        mDayShiNo = mDayKanShiNo.minus(1).rem(12) + 1

        mFatalOrder = when ((mGender + mYearKanShiNo) % 2) {
            0 -> 1
            1 -> -1
            else -> {
                Log.e(TAG, "onCreate: The variable mFatalOrder must be set to a value other than $mFatalOrder.")
            }
        }

        if (mFatalOrder == 1)
            mDiffLastDay = setDiffLastDay()

        mTaiStartAge = when (mFatalOrder) {
            1 -> (mDiffLastDay.toFloat() / 3).roundToInt()
            -1 -> (mDiffFirstDay.toFloat() / 3).roundToInt()
            else -> {
                Log.e(TAG, "onCreate: The variable mTaiStartAge must be set to a value other than $mTaiStartAge.")
            }
        }

        mTaiKanNo = (abs(mMonthKanNo + (1 - mFatalOrder) * 10 + mFatalOrder - 1)).rem(10) + 1
        mTaiShiNo = (abs(mMonthShiNo + (1 - mFatalOrder) * 12 + mFatalOrder - 1)).rem(12) + 1
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

    /**
     * Calculate and set the difference from the beginning of the month to the birthday
     */
    private fun setDiffFirstDay(): Int {
        val birthday = LocalDate.of(mYear, mMonth, mDay)
        val startDay = LocalDate.of(mFirstDay.div(10000), mFirstDay.mod(10000).div(100), mFirstDay.mod(100))

        return ChronoUnit.DAYS.between(startDay, birthday).toInt()
    }

    /**
     * Calculate and set the difference from the end of the month to the birthday
     */
    private fun setDiffLastDay(): Int {
        val birthday = LocalDate.of(mYear, mMonth, mDay)
        val nextStartDay = mAppDBHelper.getNextFirstDay(mYear, mMonth, mDay).toInt()
        val lastDay = LocalDate.of(nextStartDay.div(10000), nextStartDay.mod(10000).div(100), nextStartDay.mod(100))

        return ChronoUnit.DAYS.between(birthday, lastDay).toInt()
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