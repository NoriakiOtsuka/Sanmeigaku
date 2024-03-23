package com.example.sanmeigaku

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.ZouKan
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.databinding.FragmentMeishikiBinding

class MeishikiFragment : Fragment() {
    private val TAG: String = "MeishikiFragment"
    private var _binding: FragmentMeishikiBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of user info received from the assessment activity */
    private val mName: String = activity.mName
    private val mYear: Int = activity.mYear
    private val mMonth: Int = activity.mMonth
    private val mDay: Int = activity.mDay
    private var mGender: Int = activity.mGender
    private var mAge: Int = activity.mAge

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanShiNo: Int = activity.mYearKanShiNo
    private val mDayKanShiNo: Int = activity.mDayKanShiNo
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variable of the difference from the beginning of the month to the birthday received from the assessment activity */
    private val mDiffFirstDay: Int = activity.mDiffFirstDay

    /** Variables of zou-kan number */
    private var mYearZouKanShoNo: Int = 0
    private var mYearZouKanChuNo: Int = 0
    private var mYearZouKanHonNo: Int = 0
    private var mMonthZouKanShoNo: Int = 0
    private var mMonthZouKanChuNo: Int = 0
    private var mMonthZouKanHonNo: Int = 0
    private var mDayZouKanShoNo: Int = 0
    private var mDayZouKanChuNo: Int = 0
    private var mDayZouKanHonNo: Int = 0

    /**
     * Create meishiki fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create meishiki fragment")
    }

    /**
     * Create meishiki fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeishikiBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: meishiki fragment view create")

        return binding.root
    }

    /**
     * Created meishiki fragment view
     */
    @SuppressLint("StringFormatMatches")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: meishiki fragment view created")

        binding.nameText.text = mName
        binding.birthdayText.text = getString(R.string.meishiki_birthday_text, mYear, mMonth, mDay)
        binding.ageText.text = getString(R.string.meishiki_age_text, mAge)
        binding.genderText.text = when (mGender) {
            1 -> getString(R.string.meishiki_gender_male_text)
            2 -> getString(R.string.meishiki_gender_female_text)
            else -> ""
        }

        setZoukanNo()
        setMeishikiTable()
    }

    /**
     * Destroy meishiki fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: meishiki fragment view destroyed")
    }

    /**
     * Set zou-kan number
     */
    private fun setZoukanNo() {
        mYearZouKanShoNo = ZouKan.valueOf("ZouKan$mYearShiNo").shoNo
        mYearZouKanChuNo = ZouKan.valueOf("ZouKan$mYearShiNo").chuNo
        mYearZouKanHonNo = ZouKan.valueOf("ZouKan$mYearShiNo").honNo
        mMonthZouKanShoNo = ZouKan.valueOf("ZouKan$mMonthShiNo").shoNo
        mMonthZouKanChuNo = ZouKan.valueOf("ZouKan$mMonthShiNo").chuNo
        mMonthZouKanHonNo = ZouKan.valueOf("ZouKan$mMonthShiNo").honNo
        mDayZouKanShoNo = ZouKan.valueOf("ZouKan$mDayShiNo").shoNo
        mDayZouKanChuNo = ZouKan.valueOf("ZouKan$mDayShiNo").chuNo
        mDayZouKanHonNo = ZouKan.valueOf("ZouKan$mDayShiNo").honNo
    }

    /**
     * Set meishiki table
     */
    private fun setMeishikiTable() {
        val yearKan = KanShi.valueOf("Kan$mYearKanNo").value
        val yearShi = KanShi.valueOf("Shi$mYearShiNo").value
        val monthKan = KanShi.valueOf("Kan$mMonthKanNo").value
        val monthShi = KanShi.valueOf("Shi$mMonthShiNo").value
        val dayKan = KanShi.valueOf("Kan$mDayKanNo").value
        val dayShi = KanShi.valueOf("Shi$mDayShiNo").value

        binding.meishikiTable.yearKanText.text = yearKan
        binding.meishikiTable.yearShiText.text = yearShi
        binding.meishikiTable.monthKanText.text = monthKan
        binding.meishikiTable.monthShiText.text = monthShi
        binding.meishikiTable.dayKanText.text = dayKan
        binding.meishikiTable.dayShiText.text = dayShi

        binding.meishikiTable.yearKanText.setTextColor(getKanColor(mYearKanNo))
        binding.meishikiTable.yearShiText.setTextColor(getShiColor(mYearShiNo))
        binding.meishikiTable.monthKanText.setTextColor(getKanColor(mMonthKanNo))
        binding.meishikiTable.monthShiText.setTextColor(getShiColor(mMonthShiNo))
        binding.meishikiTable.dayKanText.setTextColor(getKanColor(mDayKanNo))
        binding.meishikiTable.dayShiText.setTextColor(getShiColor(mDayShiNo))

        val yearZouKanSho = ZouKan.valueOf("ZouKan$mYearShiNo").shogen
        val yearZouKanChu = ZouKan.valueOf("ZouKan$mYearShiNo").chugen
        val yearZouKanHon = ZouKan.valueOf("ZouKan$mYearShiNo").hongen
        val monthZouKanSho = ZouKan.valueOf("ZouKan$mMonthShiNo").shogen
        val monthZouKanChu = ZouKan.valueOf("ZouKan$mMonthShiNo").chugen
        val monthZouKanHon = ZouKan.valueOf("ZouKan$mMonthShiNo").hongen
        val dayZouKanSho = ZouKan.valueOf("ZouKan$mDayShiNo").shogen
        val dayZouKanChu = ZouKan.valueOf("ZouKan$mDayShiNo").chugen
        val dayZouKanHon = ZouKan.valueOf("ZouKan$mDayShiNo").hongen

        binding.meishikiTable.yearZouKanShoText.text = yearZouKanSho
        binding.meishikiTable.yearZouKanChuText.text = yearZouKanChu
        binding.meishikiTable.yearZouKanHonText.text = yearZouKanHon
        binding.meishikiTable.monthZouKanShoText.text = monthZouKanSho
        binding.meishikiTable.monthZouKanChuText.text = monthZouKanChu
        binding.meishikiTable.monthZouKanHonText.text = monthZouKanHon
        binding.meishikiTable.dayZouKanShoText.text = dayZouKanSho
        binding.meishikiTable.dayZouKanChuText.text = dayZouKanChu
        binding.meishikiTable.dayZouKanHonText.text = dayZouKanHon

        binding.meishikiTable.yearZouKanShoText.setTextColor(getKanColor(mYearZouKanShoNo))
        binding.meishikiTable.yearZouKanChuText.setTextColor(getKanColor(mYearZouKanChuNo))
        binding.meishikiTable.yearZouKanHonText.setTextColor(getKanColor(mYearZouKanHonNo))
        binding.meishikiTable.monthZouKanShoText.setTextColor(getKanColor(mMonthZouKanShoNo))
        binding.meishikiTable.monthZouKanChuText.setTextColor(getKanColor(mMonthZouKanChuNo))
        binding.meishikiTable.monthZouKanHonText.setTextColor(getKanColor(mMonthZouKanHonNo))
        binding.meishikiTable.dayZouKanShoText.setTextColor(getKanColor(mDayZouKanShoNo))
        binding.meishikiTable.dayZouKanChuText.setTextColor(getKanColor(mDayZouKanChuNo))
        binding.meishikiTable.dayZouKanHonText.setTextColor(getKanColor(mDayZouKanHonNo))

        val util = Utility()
        val yearZouKanType = util.getZonKanType(mYearShiNo, mDiffFirstDay)
        val monthZouKanType = util.getZonKanType(mMonthShiNo, mDiffFirstDay)
        val dayZouKanType = util.getZonKanType(mDayShiNo, mDiffFirstDay)

        when (yearZouKanType) {
            1 -> binding.meishikiTable.yearZouKanShoFlag.visibility = View.VISIBLE
            2 -> binding.meishikiTable.yearZouKanChuFlag.visibility = View.VISIBLE
            3 -> binding.meishikiTable.yearZouKanHonFlag.visibility = View.VISIBLE
        }
        when (monthZouKanType) {
            1 -> binding.meishikiTable.monthZouKanShoFlag.visibility = View.VISIBLE
            2 -> binding.meishikiTable.monthZouKanChuFlag.visibility = View.VISIBLE
            3 -> binding.meishikiTable.monthZouKanHonFlag.visibility = View.VISIBLE
        }
        when (dayZouKanType) {
            1 -> binding.meishikiTable.dayZouKanShoFlag.visibility = View.VISIBLE
            2 -> binding.meishikiTable.dayZouKanChuFlag.visibility = View.VISIBLE
            3 -> binding.meishikiTable.dayZouKanHonFlag.visibility = View.VISIBLE
        }

        val shouraiTenChuSatsu1 = KanShi.valueOf("Shi${getTenChuSatsu(mDayKanShiNo).first}").value
        val shouraiTenChuSatsu2 = KanShi.valueOf("Shi${getTenChuSatsu(mDayKanShiNo).second}").value
        val tenChuSatsuFromYear1 = KanShi.valueOf("Shi${getTenChuSatsu(mYearKanShiNo).first}").value
        val tenChuSatsuFromYear2 = KanShi.valueOf("Shi${getTenChuSatsu(mYearKanShiNo).second}").value

        binding.meishikiTable.shouraiTenChuSatsu1.text = shouraiTenChuSatsu1
        binding.meishikiTable.shouraiTenChuSatsu2.text = shouraiTenChuSatsu2
        binding.meishikiTable.tenChuSatsuFromYear1.text = tenChuSatsuFromYear1
        binding.meishikiTable.tenChuSatsuFromYear2.text = tenChuSatsuFromYear2
    }

    /**
     * Get the kan color
     */
    private fun getKanColor(kanNo: Int): Int {
        var color = Color.BLACK
        when (kanNo) {
            1, 2 -> color = resources.getColor(R.color.gogyou_moku)
            3, 4 -> color = resources.getColor(R.color.gogyou_ka)
            5, 6 -> color = resources.getColor(R.color.gogyou_do)
            7, 8 -> color = resources.getColor(R.color.gogyou_gon)
            9, 10 -> color = resources.getColor(R.color.gogyou_sui)
        }

        return color
    }

    /**
     * Get the shi color
     */
    private fun getShiColor(shiNo: Int): Int {
        var color = Color.BLACK
        when (shiNo) {
            3, 4 -> color = resources.getColor(R.color.gogyou_moku)
            6, 7 -> color = resources.getColor(R.color.gogyou_ka)
            2, 5, 8, 11 -> color = resources.getColor(R.color.gogyou_do)
            9, 10 -> color = resources.getColor(R.color.gogyou_gon)
            1, 12 -> color = resources.getColor(R.color.gogyou_sui)
        }

        return color
    }

    /**
     * Get params of TenChuSatsu
     */
    private fun getTenChuSatsu(kanShiNo: Int): Pair<Int, Int> {
        var param1 = 0
        var param2 = 0
        when (kanShiNo) {
            in 1..10 -> {
                param1 = 11
                param2 = 12
            }
            in 11..20 -> {
                param1 = 9
                param2 = 10
            }
            in 21..30 -> {
                param1 = 7
                param2 = 8
            }
            in 31..40 -> {
                param1 = 5
                param2 = 6
            }
            in 41..50 -> {
                param1 = 3
                param2 = 4
            }
            in 51..60 -> {
                param1 = 1
                param2 = 2
            }
        }

        return Pair(param1, param2)
    }
}