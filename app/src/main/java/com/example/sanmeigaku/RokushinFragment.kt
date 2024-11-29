package com.example.sanmeigaku

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.Enum.RokuFemale
import com.example.sanmeigaku.Enum.RokuMale
import com.example.sanmeigaku.Enum.RokuRelative
import com.example.sanmeigaku.Enum.ZouKan
import com.example.sanmeigaku.databinding.FragmentRokushinBinding

class RokushinFragment : Fragment() {
    private val TAG: String = "RokushinFragment"
    private var _binding: FragmentRokushinBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of user info received from the assessment activity */
    private var mGender: Int = activity.mGender

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variables to manage the value of jikkan to be displayed in junishinkan hou chart */
    private lateinit var mJuniKanNum: IntArray
    private var mIsJuniKanArray: BooleanArray = BooleanArray(10) { false }
    private var mIsRelativeArray: BooleanArray = BooleanArray(14) { true }

    /** Base numbers that manage to show jikkan for each relative in junishinkan hou */
    private var mMyMotherBaseNo: Int = mDayKanNo
    private var mMyFatherBaseNo: Int = mMyMotherBaseNo
    private var mPgBaseNo: Int = mMyFatherBaseNo
    private var mMgBaseNo: Int = mMyMotherBaseNo
    private var mSpouseBaseNo: Int = mDayKanNo
    private var mSpouseMotherBaseNo: Int = mSpouseBaseNo
    private var mSonBaseNo: Int = mSpouseBaseNo
    private var mDaughterBaseNo: Int = mSpouseBaseNo

    /** Determine if the number is odd or even */
    private val Int.isOdd: Boolean
        get() = this.rem(2) == 1

    /**
     * Create rokushin fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create rokushin fragment")
    }

    /**
     * Create rokushin fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRokushinBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: rokushin fragment view create")

        if (mGender == 1) {
            binding.rokushinRokuFemaleChart.rokuFemaleChart.isVisible = false
            binding.rokushinJuniFemaleChart.rokuFemaleChart.isVisible = false
        } else {
            binding.rokushinRokuMaleChart.rokuMaleChart.isVisible = false
            binding.rokushinJuniMaleChart.rokuMaleChart.isVisible = false
        }

        return binding.root
    }

    /**
     * Created rokushin fragment view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: rokushin fragment view created")

        setRokushinChart()
        setJuniShinKanList()
        setJuniShinKanChart()
    }

    /**
     * Destroy rokushin fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: rokushin fragment view destroyed")
    }

    /**
     * Set up a correlation chart of the jikkan in the rokushin hou
     */
    private fun setRokushinChart() {
        if (mGender == 1) {
            val rokushinChart = binding.rokushinRokuMaleChart
            val rokuKanNum = maleRokuNumArray()
            rokushinChart.rokuMyself.text = KanShi.valueOf("Kan${rokuKanNum[0]}").value
            rokushinChart.rokuSpouse.text = KanShi.valueOf("Kan${rokuKanNum[1]}").value
            rokushinChart.rokuMyFather.text = KanShi.valueOf("Kan${rokuKanNum[2]}").value
            rokushinChart.rokuMyMother.text = KanShi.valueOf("Kan${rokuKanNum[3]}").value
            rokushinChart.rokuPaternalGrandfather.text = KanShi.valueOf("Kan${rokuKanNum[4]}").value
            rokushinChart.rokuPaternalGrandmother.text = KanShi.valueOf("Kan${rokuKanNum[5]}").value
            rokushinChart.rokuMaternalGrandfather.text = KanShi.valueOf("Kan${rokuKanNum[6]}").value
            rokushinChart.rokuMaternalGrandmother.text = KanShi.valueOf("Kan${rokuKanNum[7]}").value
            rokushinChart.rokuSpouseFather.text = KanShi.valueOf("Kan${rokuKanNum[8]}").value
            rokushinChart.rokuSpouseMother.text = KanShi.valueOf("Kan${rokuKanNum[9]}").value
            rokushinChart.rokuSon.text = KanShi.valueOf("Kan${rokuKanNum[10]}").value
            rokushinChart.rokuSonSpouse.text = KanShi.valueOf("Kan${rokuKanNum[11]}").value
            rokushinChart.rokuDaughter.text = KanShi.valueOf("Kan${rokuKanNum[12]}").value
            rokushinChart.rokuDaughterSpouse.text = KanShi.valueOf("Kan${rokuKanNum[13]}").value
        } else if (mGender == 2) {
            val rokushinChart = binding.rokushinRokuFemaleChart
            val rokuKanNum = femaleRokuNumArray()
            rokushinChart.rokuMyself.text = KanShi.valueOf("Kan${rokuKanNum[0]}").value
            rokushinChart.rokuSpouse.text = KanShi.valueOf("Kan${rokuKanNum[1]}").value
            rokushinChart.rokuMyFather.text = KanShi.valueOf("Kan${rokuKanNum[2]}").value
            rokushinChart.rokuMyMother.text = KanShi.valueOf("Kan${rokuKanNum[3]}").value
            rokushinChart.rokuPaternalGrandfather.text = KanShi.valueOf("Kan${rokuKanNum[4]}").value
            rokushinChart.rokuPaternalGrandmother.text = KanShi.valueOf("Kan${rokuKanNum[5]}").value
            rokushinChart.rokuMaternalGrandfather.text = KanShi.valueOf("Kan${rokuKanNum[6]}").value
            rokushinChart.rokuMaternalGrandmother.text = KanShi.valueOf("Kan${rokuKanNum[7]}").value
            rokushinChart.rokuSpouseFather.text = KanShi.valueOf("Kan${rokuKanNum[8]}").value
            rokushinChart.rokuSpouseMother.text = KanShi.valueOf("Kan${rokuKanNum[9]}").value
            rokushinChart.rokuSon.text = KanShi.valueOf("Kan${rokuKanNum[10]}").value
            rokushinChart.rokuSonSpouse.text = KanShi.valueOf("Kan${rokuKanNum[11]}").value
            rokushinChart.rokuDaughter.text = KanShi.valueOf("Kan${rokuKanNum[12]}").value
            rokushinChart.rokuDaughterSpouse.text = KanShi.valueOf("Kan${rokuKanNum[13]}").value
        }
    }

    /**
     * Set up a jikkan listing to display chart junishinkan hou
     */
    private fun setJuniShinKanList() {
        if (mGender == 1) {
            mJuniKanNum = femaleRokuNumArray()
        } else if (mGender == 2) {
            mJuniKanNum = maleRokuNumArray()
        }

        val kanList = intArrayOf(
            mYearKanNo,
            mMonthKanNo,
            mDayKanNo,
            ZouKan.valueOf("ZouKan$mYearShiNo").shoNo,
            ZouKan.valueOf("ZouKan$mYearShiNo").chuNo,
            ZouKan.valueOf("ZouKan$mYearShiNo").honNo,
            ZouKan.valueOf("ZouKan$mMonthShiNo").shoNo,
            ZouKan.valueOf("ZouKan$mMonthShiNo").chuNo,
            ZouKan.valueOf("ZouKan$mMonthShiNo").honNo,
            ZouKan.valueOf("ZouKan$mDayShiNo").shoNo,
            ZouKan.valueOf("ZouKan$mDayShiNo").chuNo,
            ZouKan.valueOf("ZouKan$mDayShiNo").honNo
        )
        for (i in kanList) {
            if (i > 0)
                mIsJuniKanArray[i - 1] = true
        }

        adjustMyMother()
        adjustMyFather()
        adjustPgMother()
        adjustPgFather()
        adjustMgMother()
        adjustMgFather()
        adjustSpouse()
        adjustSpouseMother()
        adjustSpouseFather()
        adjustSon()
        adjustSonSpouse()
        adjustDaughter()
        adjustDaughterSpouse()
    }

    /**
     * An array of numbers corresponding to each Rokushin element for males
     */
    private fun maleRokuNumArray(): IntArray {
        return intArrayOf(
            RokuMale.valueOf("Roku$mDayKanNo").myself,
            RokuMale.valueOf("Roku$mDayKanNo").spouse,
            RokuMale.valueOf("Roku$mDayKanNo").myFather,
            RokuMale.valueOf("Roku$mDayKanNo").myMother,
            RokuMale.valueOf("Roku$mDayKanNo").pgFather,
            RokuMale.valueOf("Roku$mDayKanNo").pgMother,
            RokuMale.valueOf("Roku$mDayKanNo").mgFather,
            RokuMale.valueOf("Roku$mDayKanNo").mgMother,
            RokuMale.valueOf("Roku$mDayKanNo").spouseFather,
            RokuMale.valueOf("Roku$mDayKanNo").spouseMother,
            RokuMale.valueOf("Roku$mDayKanNo").son,
            RokuMale.valueOf("Roku$mDayKanNo").sonSpouse,
            RokuMale.valueOf("Roku$mDayKanNo").daughter,
            RokuMale.valueOf("Roku$mDayKanNo").daughterSpouse
        )
    }

    /**
     * An array of numbers corresponding to each Rokushin element for females
     */
    private fun femaleRokuNumArray(): IntArray {
        return intArrayOf(
            RokuFemale.valueOf("Roku$mDayKanNo").myself,
            RokuFemale.valueOf("Roku$mDayKanNo").spouse,
            RokuFemale.valueOf("Roku$mDayKanNo").myFather,
            RokuFemale.valueOf("Roku$mDayKanNo").myMother,
            RokuFemale.valueOf("Roku$mDayKanNo").pgFather,
            RokuFemale.valueOf("Roku$mDayKanNo").pgMother,
            RokuFemale.valueOf("Roku$mDayKanNo").mgFather,
            RokuFemale.valueOf("Roku$mDayKanNo").mgMother,
            RokuFemale.valueOf("Roku$mDayKanNo").spouseFather,
            RokuFemale.valueOf("Roku$mDayKanNo").spouseMother,
            RokuFemale.valueOf("Roku$mDayKanNo").son,
            RokuFemale.valueOf("Roku$mDayKanNo").sonSpouse,
            RokuFemale.valueOf("Roku$mDayKanNo").daughter,
            RokuFemale.valueOf("Roku$mDayKanNo").daughterSpouse
        )
    }

    /**
     * Adjust one's own mother's jikkan for the junishinkan hou
     */
    private fun adjustMyMother() {
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mMyMotherBaseNo").myMother
            2 -> RokuFemale.valueOf("Roku$mMyMotherBaseNo").myMother
            else -> {
                mIsRelativeArray[3] = false
                return
            }
        }
        adjustJuniKan(kanNo, 3)
    }

    /**
     * Adjust one's own father's jikkan for the junishinkan hou
     */
    private fun adjustMyFather() {
        if (!mIsRelativeArray[3]) {
            mIsRelativeArray[2] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${2}").value} is already set to empty")
            return
        }

        mMyFatherBaseNo = mMyMotherBaseNo
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mMyFatherBaseNo").myFather
            2 -> RokuFemale.valueOf("Roku$mMyFatherBaseNo").myFather
            else -> {
                mIsRelativeArray[2] = false
                return
            }
        }
        mJuniKanNum[2] = kanNo
        adjustJuniKan(kanNo, 2)
    }

    /**
     * Adjust one's own paternal grandmother's jikkan for the junishinkan hou
     */
    private fun adjustPgMother() {
        if (!mIsRelativeArray[2]) {
            mIsRelativeArray[5] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${5}").value} is already set to empty")
            return
        }

        mPgBaseNo = mMyFatherBaseNo
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mPgBaseNo").pgMother
            2 -> RokuFemale.valueOf("Roku$mPgBaseNo").pgMother
            else -> {
                mIsRelativeArray[5] = false
                return
            }
        }
        mJuniKanNum[5] = kanNo
        adjustJuniKan(kanNo, 5)
    }

    /**
     * Adjust one's own paternal grandfather's jikkan for the junishinkan hou
     */
    private fun adjustPgFather() {
        if (!mIsRelativeArray[5]) {
            mIsRelativeArray[4] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${4}").value} is already set to empty")
            return
        }

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mPgBaseNo").pgFather
            2 -> RokuFemale.valueOf("Roku$mPgBaseNo").pgFather
            else -> {
                mIsRelativeArray[4] = false
                return
            }
        }
        mJuniKanNum[4] = kanNo
        adjustJuniKan(kanNo, 4)
    }

    /**
     * Adjust one's own maternal grandmother's jikkan for the junishinkan hou
     */
    private fun adjustMgMother() {
        if (!mIsRelativeArray[3]) {
            mIsRelativeArray[7] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${7}").value} is already set to empty")
            return
        }

        mMgBaseNo = mMyMotherBaseNo
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mMgBaseNo").mgMother
            2 -> RokuFemale.valueOf("Roku$mMgBaseNo").mgMother
            else -> {
                mIsRelativeArray[7] = false
                return
            }
        }
        mJuniKanNum[7] = kanNo
        adjustJuniKan(kanNo, 7)
    }

    /**
     * Adjust one's own maternal grandfather's jikkan for the junishinkan hou
     */
    private fun adjustMgFather() {
        if (!mIsRelativeArray[7]) {
            mIsRelativeArray[6] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${6}").value} is already set to empty")
            return
        }

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mMgBaseNo").mgFather
            2 -> RokuFemale.valueOf("Roku$mMgBaseNo").mgFather
            else -> {
                mIsRelativeArray[6] = false
                return
            }
        }
        mJuniKanNum[6] = kanNo
        adjustJuniKan(kanNo, 6)
    }

    /**
     * Adjust spouse's jikkan for the junishinkan hou
     */
    private fun adjustSpouse() {
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mSpouseBaseNo").spouse
            2 -> RokuFemale.valueOf("Roku$mSpouseBaseNo").spouse
            else -> {
                mIsRelativeArray[1] = false
                return
            }
        }
        adjustJuniKan(kanNo, 1)
    }

    /**
     * Adjust spouse mother's jikkan for the junishinkan hou
     */
    private fun adjustSpouseMother() {
        if (!mIsRelativeArray[1]) {
            mIsRelativeArray[9] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${9}").value} is already set to empty")
            return
        }

        mSpouseMotherBaseNo = mSpouseBaseNo
        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mSpouseMotherBaseNo").spouseMother
            2 -> RokuFemale.valueOf("Roku$mSpouseMotherBaseNo").spouseMother
            else -> {
                mIsRelativeArray[9] = false
                return
            }
        }
        mJuniKanNum[9] = kanNo
        adjustJuniKan(kanNo, 9)
    }

    /**
     * Adjust spouse father's jikkan for the junishinkan hou
     */
    private fun adjustSpouseFather() {
        if (!mIsRelativeArray[9]) {
            mIsRelativeArray[8] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${8}").value} is already set to empty")
            return
        }

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mSpouseMotherBaseNo").spouseFather
            2 -> RokuFemale.valueOf("Roku$mSpouseMotherBaseNo").spouseFather
            else -> {
                mIsRelativeArray[8] = false
                return
            }
        }
        mJuniKanNum[8] = kanNo
        adjustJuniKan(kanNo, 8)
    }

    /**
     * Adjust son's jikkan for the junishinkan hou
     */
    private fun adjustSon() {
        if ((!mIsRelativeArray[1]) && (mGender == 1)) {
            mIsRelativeArray[10] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${10}").value} is already set to empty")
            return
        }

        if (mGender == 1)
            mSonBaseNo = mSpouseBaseNo

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mSonBaseNo").son
            2 -> RokuFemale.valueOf("Roku$mSonBaseNo").son
            else -> {
                mIsRelativeArray[10] = false
                return
            }
        }
        mJuniKanNum[10] = kanNo
        adjustJuniKan(kanNo, 10)
    }

    /**
     * Adjust son's spouse's jikkan for the junishinkan hou
     */
    private fun adjustSonSpouse() {
        if (!mIsRelativeArray[10]) {
            mIsRelativeArray[11] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${11}").value} is already set to empty")
            return
        }

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mSonBaseNo").sonSpouse
            2 -> RokuFemale.valueOf("Roku$mSonBaseNo").sonSpouse
            else -> {
                mIsRelativeArray[11] = false
                return
            }
        }
        mJuniKanNum[11] = kanNo
        adjustJuniKan(kanNo, 11)
    }

    /**
     * Adjust daughter's jikkan for the junishinkan hou
     */
    private fun adjustDaughter() {
        if ((!mIsRelativeArray[1]) && (mGender == 1)) {
            mIsRelativeArray[12] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${12}").value} is already set to empty")
            return
        }

        if (mGender == 1)
            mDaughterBaseNo = mSpouseBaseNo

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mDaughterBaseNo").daughter
            2 -> RokuFemale.valueOf("Roku$mDaughterBaseNo").daughter
            else -> {
                mIsRelativeArray[12] = false
                return
            }
        }
        mJuniKanNum[12] = kanNo
        adjustJuniKan(kanNo, 12)
    }

    /**
     * Adjust daughter's spouse's jikkan for the junishinkan hou
     */
    private fun adjustDaughterSpouse() {
        if (!mIsRelativeArray[12]) {
            mIsRelativeArray[13] = false
            Log.i(TAG, "${RokuRelative.valueOf("Roku${13}").value} is already set to empty")
            return
        }

        val kanNo = when (mGender) {
            1 -> RokuMale.valueOf("Roku$mDaughterBaseNo").daughterSpouse
            2 -> RokuFemale.valueOf("Roku$mDaughterBaseNo").daughterSpouse
            else -> {
                mIsRelativeArray[13] = false
                return
            }
        }
        mJuniKanNum[13] = kanNo
        adjustJuniKan(kanNo, 13)
    }

    /**
     * Adjust the value of jikkan for the junishinkan hou
     */
    private fun adjustJuniKan(kanNum: Int, relativeNum: Int) {
        val relative = RokuRelative.valueOf("Roku${relativeNum}").value

        if (mIsJuniKanArray[kanNum - 1]) {
            Log.i(TAG, "$relative does not need to be renewed")
            return
        }

        val invertNo = invertNum(kanNum)
        val baseNo = when (kanNum.isOdd) {
            true -> when (relativeNum) {
                1 -> mSpouseBaseNo.minus(1).plus(1).rem(10).plus(1)
                2 -> mMyFatherBaseNo.minus(1).plus(3).rem(10).plus(1)
                3 -> mMyMotherBaseNo.minus(1).plus(9).rem(10).plus(1)
                4 -> mPgBaseNo.minus(1).plus(5).rem(10).plus(1)
                5 -> mPgBaseNo.minus(1).plus(7).rem(10).plus(1)
                6 -> mMgBaseNo.minus(1).plus(1).rem(10).plus(1)
                7 -> invertNum(mMgBaseNo)
                8 -> mSpouseMotherBaseNo.minus(1).plus(3).rem(10).plus(1)
                9 -> mSpouseMotherBaseNo.minus(1).plus(9).rem(10).plus(1)
                10 -> mSonBaseNo.minus(1).plus(9).rem(10).plus(1)
                11 -> mSonBaseNo.minus(1).plus(3).rem(10).plus(1)
                12 -> mDaughterBaseNo.minus(1).plus(1).rem(10).plus(1)
                13 -> mDaughterBaseNo.minus(1).plus(1).rem(10).plus(1)
                else -> return
            }
            false -> when (relativeNum) {
                1 -> mSpouseBaseNo.minus(1).plus(9).rem(10).plus(1)
                2 -> mMyFatherBaseNo.minus(1).plus(7).rem(10).plus(1)
                3 -> mMyMotherBaseNo.minus(1).plus(1).rem(10).plus(1)
                4 -> mPgBaseNo.minus(1).plus(5).rem(10).plus(1)
                5 -> mPgBaseNo.minus(1).plus(3).rem(10).plus(1)
                6 -> mMgBaseNo.minus(1).plus(9).rem(10).plus(1)
                7 -> invertNum(mMgBaseNo)
                8 -> mSpouseMotherBaseNo.minus(1).plus(7).rem(10).plus(1)
                9 -> mSpouseMotherBaseNo.minus(1).plus(1).rem(10).plus(1)
                10 -> mSonBaseNo.minus(1).plus(1).rem(10).plus(1)
                11 -> mSonBaseNo.minus(1).plus(7).rem(10).plus(1)
                12 -> mDaughterBaseNo.minus(1).plus(9).rem(10).plus(1)
                13 -> mDaughterBaseNo.minus(1).plus(9).rem(10).plus(1)
                else -> return
            }
        }

        if (mIsJuniKanArray[invertNo - 1]) {
            if (mGender == 1) {
                updateMaleJuniKan(baseNo, relativeNum)
            } else if (mGender == 2) {
                updateFemaleJuniKan(baseNo, relativeNum)
            }
            Log.i(TAG, "$relative has been updated")
        } else {
            mIsRelativeArray[relativeNum] = false
            Log.i(TAG, "$relative is set to empty")
        }
    }

    /**
     * Invert odd and even number
     */
    private fun invertNum(modNum: Int): Int {
        return when (modNum.isOdd) {
            true -> modNum.plus(1)
            false -> modNum.minus(1)
        }
    }

    /**
     * Update the value of jikkan for the junishinkan hou in A in the case of male
     */
    private fun updateMaleJuniKan(baseNum: Int, relativeNum: Int) {
        when (relativeNum) {
            1 -> {
                mSpouseBaseNo = baseNum
                mJuniKanNum[1] = RokuMale.valueOf("Roku$baseNum").spouse
                mJuniKanNum[8] = RokuMale.valueOf("Roku$baseNum").spouseFather
                mJuniKanNum[9] = RokuMale.valueOf("Roku$baseNum").spouseMother
                mJuniKanNum[10] = RokuMale.valueOf("Roku$baseNum").son
                mJuniKanNum[11] = RokuMale.valueOf("Roku$baseNum").sonSpouse
                mJuniKanNum[12] = RokuMale.valueOf("Roku$baseNum").daughter
                mJuniKanNum[13] = RokuMale.valueOf("Roku$baseNum").daughterSpouse
            }
            2 -> {
                mMyFatherBaseNo = baseNum
                mJuniKanNum[2] = RokuMale.valueOf("Roku$baseNum").myFather
                mJuniKanNum[4] = RokuMale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuMale.valueOf("Roku$baseNum").pgMother
            }
            3 -> {
                mMyMotherBaseNo = baseNum
                mJuniKanNum[2] = RokuMale.valueOf("Roku$baseNum").myFather
                mJuniKanNum[3] = RokuMale.valueOf("Roku$baseNum").myMother
                mJuniKanNum[4] = RokuMale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuMale.valueOf("Roku$baseNum").pgMother
                mJuniKanNum[6] = RokuMale.valueOf("Roku$baseNum").mgFather
                mJuniKanNum[7] = RokuMale.valueOf("Roku$baseNum").mgMother
            }
            4 -> mJuniKanNum[4] = RokuMale.valueOf("Roku$baseNum").pgFather
            5 -> {
                mPgBaseNo = baseNum
                mJuniKanNum[4] = RokuMale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuMale.valueOf("Roku$baseNum").pgMother
            }
            6 -> mJuniKanNum[6] = RokuMale.valueOf("Roku$baseNum").mgFather
            7 -> {
                mMgBaseNo = baseNum
                mJuniKanNum[6] = RokuMale.valueOf("Roku$baseNum").mgFather
                mJuniKanNum[7] = RokuMale.valueOf("Roku$baseNum").mgMother
            }
            8 -> mJuniKanNum[8] = RokuMale.valueOf("Roku$baseNum").spouseFather
            9 -> {
                mSpouseMotherBaseNo = baseNum
                mJuniKanNum[8] = RokuMale.valueOf("Roku$baseNum").spouseFather
                mJuniKanNum[9] = RokuMale.valueOf("Roku$baseNum").spouseMother
            }
            10 -> {
                mSonBaseNo = baseNum
                mJuniKanNum[10] = RokuMale.valueOf("Roku$baseNum").son
                mJuniKanNum[11] = RokuMale.valueOf("Roku$baseNum").sonSpouse
            }
            11 -> mJuniKanNum[11] = RokuMale.valueOf("Roku$baseNum").sonSpouse
            12 -> {
                mDaughterBaseNo = baseNum
                mJuniKanNum[12] = RokuMale.valueOf("Roku$baseNum").daughter
                mJuniKanNum[13] = RokuMale.valueOf("Roku$baseNum").daughterSpouse
            }
            13 -> mJuniKanNum[13] = RokuMale.valueOf("Roku$baseNum").daughterSpouse
        }
    }

    /**
     * Update the value of jikkan for the junishinkan hou in A in the case of female
     */
    private fun updateFemaleJuniKan(baseNum: Int, relativeNum: Int) {
        when (relativeNum) {
            1 -> {
                mSpouseBaseNo = baseNum
                mJuniKanNum[1] = RokuFemale.valueOf("Roku$baseNum").spouse
                mJuniKanNum[8] = RokuFemale.valueOf("Roku$baseNum").spouseFather
                mJuniKanNum[9] = RokuFemale.valueOf("Roku$baseNum").spouseMother
                mJuniKanNum[10] = RokuFemale.valueOf("Roku$baseNum").son
                mJuniKanNum[11] = RokuFemale.valueOf("Roku$baseNum").sonSpouse
                mJuniKanNum[12] = RokuFemale.valueOf("Roku$baseNum").daughter
                mJuniKanNum[13] = RokuFemale.valueOf("Roku$baseNum").daughterSpouse
            }
            2 -> {
                mMyFatherBaseNo = baseNum
                mJuniKanNum[2] = RokuFemale.valueOf("Roku$baseNum").myFather
                mJuniKanNum[4] = RokuFemale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuFemale.valueOf("Roku$baseNum").pgMother
            }
            3 -> {
                mMyMotherBaseNo = baseNum
                mJuniKanNum[2] = RokuFemale.valueOf("Roku$baseNum").myFather
                mJuniKanNum[3] = RokuFemale.valueOf("Roku$baseNum").myMother
                mJuniKanNum[4] = RokuFemale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuFemale.valueOf("Roku$baseNum").pgMother
                mJuniKanNum[6] = RokuFemale.valueOf("Roku$baseNum").mgFather
                mJuniKanNum[7] = RokuFemale.valueOf("Roku$baseNum").mgMother
            }
            4 -> mJuniKanNum[4] = RokuFemale.valueOf("Roku$baseNum").pgFather
            5 -> {
                mPgBaseNo = baseNum
                mJuniKanNum[4] = RokuFemale.valueOf("Roku$baseNum").pgFather
                mJuniKanNum[5] = RokuFemale.valueOf("Roku$baseNum").pgMother
            }
            6 -> mJuniKanNum[6] = RokuFemale.valueOf("Roku$baseNum").mgFather
            7 -> {
                mMgBaseNo = baseNum
                mJuniKanNum[6] = RokuFemale.valueOf("Roku$baseNum").mgFather
                mJuniKanNum[7] = RokuFemale.valueOf("Roku$baseNum").mgMother
            }
            8 -> mJuniKanNum[8] = RokuFemale.valueOf("Roku$baseNum").spouseFather
            9 -> {
                mSpouseMotherBaseNo = baseNum
                mJuniKanNum[8] = RokuFemale.valueOf("Roku$baseNum").spouseFather
                mJuniKanNum[9] = RokuFemale.valueOf("Roku$baseNum").spouseMother
            }
            10 -> {
                mSonBaseNo = baseNum
                mJuniKanNum[10] = RokuFemale.valueOf("Roku$baseNum").son
                mJuniKanNum[11] = RokuFemale.valueOf("Roku$baseNum").sonSpouse
            }
            11 -> mJuniKanNum[11] = RokuFemale.valueOf("Roku$baseNum").sonSpouse
            12 -> {
                mDaughterBaseNo = baseNum
                mJuniKanNum[12] = RokuFemale.valueOf("Roku$baseNum").daughter
                mJuniKanNum[13] = RokuFemale.valueOf("Roku$baseNum").daughterSpouse
            }
            13 -> mJuniKanNum[13] = RokuFemale.valueOf("Roku$baseNum").daughterSpouse
        }
    }

    /**
     * Set up a correlation chart of the jikkan in the junishinkan hou
     */
    private fun setJuniShinKanChart() {
        if (mGender == 1) {
            val rokushinChart = binding.rokushinJuniMaleChart
            rokushinChart.rokuMyself.text = KanShi.valueOf("Kan${mJuniKanNum[0]}").value
            if (mIsRelativeArray[1])
                rokushinChart.rokuSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[1]}").value
            if (mIsRelativeArray[2])
                rokushinChart.rokuMyFather.text = KanShi.valueOf("Kan${mJuniKanNum[2]}").value
            if (mIsRelativeArray[3])
                rokushinChart.rokuMyMother.text = KanShi.valueOf("Kan${mJuniKanNum[3]}").value
            if (mIsRelativeArray[4])
                rokushinChart.rokuPaternalGrandfather.text = KanShi.valueOf("Kan${mJuniKanNum[4]}").value
            if (mIsRelativeArray[5])
                rokushinChart.rokuPaternalGrandmother.text = KanShi.valueOf("Kan${mJuniKanNum[5]}").value
            if (mIsRelativeArray[6])
                rokushinChart.rokuMaternalGrandfather.text = KanShi.valueOf("Kan${mJuniKanNum[6]}").value
            if (mIsRelativeArray[7])
                rokushinChart.rokuMaternalGrandmother.text = KanShi.valueOf("Kan${mJuniKanNum[7]}").value
            if (mIsRelativeArray[8])
                rokushinChart.rokuSpouseFather.text = KanShi.valueOf("Kan${mJuniKanNum[8]}").value
            if (mIsRelativeArray[9])
                rokushinChart.rokuSpouseMother.text = KanShi.valueOf("Kan${mJuniKanNum[9]}").value
            if (mIsRelativeArray[10])
                rokushinChart.rokuSon.text = KanShi.valueOf("Kan${mJuniKanNum[10]}").value
            if (mIsRelativeArray[11])
                rokushinChart.rokuSonSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[11]}").value
            if (mIsRelativeArray[12])
                rokushinChart.rokuDaughter.text = KanShi.valueOf("Kan${mJuniKanNum[12]}").value
            if (mIsRelativeArray[13])
                rokushinChart.rokuDaughterSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[13]}").value
        } else if (mGender == 2) {
            val rokushinChart = binding.rokushinJuniFemaleChart
            rokushinChart.rokuMyself.text = KanShi.valueOf("Kan${mJuniKanNum[0]}").value
            if (mIsRelativeArray[1])
                rokushinChart.rokuSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[1]}").value
            if (mIsRelativeArray[2])
                rokushinChart.rokuMyFather.text = KanShi.valueOf("Kan${mJuniKanNum[2]}").value
            if (mIsRelativeArray[3])
                rokushinChart.rokuMyMother.text = KanShi.valueOf("Kan${mJuniKanNum[3]}").value
            if (mIsRelativeArray[4])
                rokushinChart.rokuPaternalGrandfather.text = KanShi.valueOf("Kan${mJuniKanNum[4]}").value
            if (mIsRelativeArray[5])
                rokushinChart.rokuPaternalGrandmother.text = KanShi.valueOf("Kan${mJuniKanNum[5]}").value
            if (mIsRelativeArray[6])
                rokushinChart.rokuMaternalGrandfather.text = KanShi.valueOf("Kan${mJuniKanNum[6]}").value
            if (mIsRelativeArray[7])
                rokushinChart.rokuMaternalGrandmother.text = KanShi.valueOf("Kan${mJuniKanNum[7]}").value
            if (mIsRelativeArray[8])
                rokushinChart.rokuSpouseFather.text = KanShi.valueOf("Kan${mJuniKanNum[8]}").value
            if (mIsRelativeArray[9])
                rokushinChart.rokuSpouseMother.text = KanShi.valueOf("Kan${mJuniKanNum[9]}").value
            if (mIsRelativeArray[10])
                rokushinChart.rokuSon.text = KanShi.valueOf("Kan${mJuniKanNum[10]}").value
            if (mIsRelativeArray[11])
                rokushinChart.rokuSonSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[11]}").value
            if (mIsRelativeArray[12])
                rokushinChart.rokuDaughter.text = KanShi.valueOf("Kan${mJuniKanNum[12]}").value
            if (mIsRelativeArray[13])
                rokushinChart.rokuDaughterSpouse.text = KanShi.valueOf("Kan${mJuniKanNum[13]}").value
        }
    }
}