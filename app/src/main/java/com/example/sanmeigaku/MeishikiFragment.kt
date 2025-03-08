package com.example.sanmeigaku

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanmeigaku.Adapter.TaiunListAdapter
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.MainStar
import com.example.sanmeigaku.Enum.SecondStar
import com.example.sanmeigaku.Enum.ZouKan
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.ViewModel.AssessmentViewModel
import com.example.sanmeigaku.databinding.FragmentMeishikiBinding
import kotlin.math.abs

class MeishikiFragment : Fragment() {
    private val TAG: String = "MeishikiFragment"
    private var _binding: FragmentMeishikiBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val mUtil: Utility = Utility()

    /** View model for assessment */
    private lateinit var mAssessmentViewModel: AssessmentViewModel

    /** Variables of user info received from the assessment activity */
    private var mName: String = ""
    private var mKana: String = ""
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
    private val mFatalOrder: Int = activity.mFatalOrder
    private val mTaiStartAge: Int = activity.mTaiStartAge
    private val mTaiKanNo: Int = activity.mTaiKanNo
    private val mTaiShiNo: Int = activity.mTaiShiNo

    /** Variables of main star number received from the assessment activity */
    private val mMainStarNo: Int = activity.mMainStarNo
    private val mMainStar1No: Int = activity.mMainStar1No
    private val mMainStar2No: Int = activity.mMainStar2No
    private val mMainStar3No: Int = activity.mMainStar3No
    private val mMainStar4No: Int = activity.mMainStar4No

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

    companion object {
        /** Array of each taiun cycle text */
        var mTaiunCycleArray: Array<String> = Array(9) {""}
    }

    /**
     * Create meishiki fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create meishiki fragment")

        val app = requireActivity().application as MainApplication
        mAssessmentViewModel = ViewModelProvider(app).get(AssessmentViewModel::class.java)
        mName = mAssessmentViewModel.name.value.toString()
        mKana = mAssessmentViewModel.kana.value.toString()
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
        setSeizuTable()
        setTaiunList()
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

        val yearZouKanType = mUtil.getZonKanType(mYearShiNo, mDiffFirstDay)
        val monthZouKanType = mUtil.getZonKanType(mMonthShiNo, mDiffFirstDay)
        val dayZouKanType = mUtil.getZonKanType(mDayShiNo, mDiffFirstDay)

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
     * Set seizu table
     */
    private fun setSeizuTable() {
        val mainStar = MainStar.valueOf("Main$mMainStarNo").value
        val mainStar1 = MainStar.valueOf("Main$mMainStar1No").value
        val mainStar2 = MainStar.valueOf("Main$mMainStar2No").value
        val mainStar3 = MainStar.valueOf("Main$mMainStar3No").value
        val mainStar4 = MainStar.valueOf("Main$mMainStar4No").value
        binding.seizuTable.mainStarText.text = mainStar
        binding.seizuTable.mainStar1Text.text = mainStar1
        binding.seizuTable.mainStar2Text.text = mainStar2
        binding.seizuTable.mainStar3Text.text = mainStar3
        binding.seizuTable.mainStar4Text.text = mainStar4

        val secondStar1 = SecondStar.valueOf("Second${mUtil.getSecondStarNo(mDayKanNo, mDayShiNo)}").value
        val secondStar2 = SecondStar.valueOf("Second${mUtil.getSecondStarNo(mDayKanNo, mMonthShiNo)}").value
        val secondStar3 = SecondStar.valueOf("Second${mUtil.getSecondStarNo(mDayKanNo, mYearShiNo)}").value
        binding.seizuTable.secondStar1Text.text = secondStar1
        binding.seizuTable.secondStar2Text.text = secondStar2
        binding.seizuTable.secondStar3Text.text = secondStar3
    }

    /**
     * Set taiun list
     */
    private fun setTaiunList() {
        if ((mFatalOrder == 0) || (mTaiStartAge == -1))
            return

        val cycleArray = resources.getStringArray(R.array.meishiki_taiun_cycle_array)
        for ((index, cycle) in cycleArray.withIndex()) {
            mTaiunCycleArray[index] = cycle
        }

        val taiunList = Array(cycleArray.size) {IntArray(3)}
        for (i in taiunList.indices) {
            taiunList[i][0] = mTaiStartAge + i * 10
            taiunList[i][1] = abs(mTaiKanNo + (1 - mFatalOrder) * 10 + mFatalOrder * i - 1).rem(10) + 1
            taiunList[i][2] = abs(mTaiShiNo + (1 - mFatalOrder) * 12 + mFatalOrder * i - 1).rem(12) + 1
        }

        binding.taiunList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TaiunListAdapter(taiunList)
        }
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