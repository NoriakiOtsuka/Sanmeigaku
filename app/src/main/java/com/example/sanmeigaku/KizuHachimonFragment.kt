package com.example.sanmeigaku

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanmeigaku.Enum.ZouKan
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.databinding.FragmentKizuHachimonBinding

class KizuHachimonFragment : Fragment() {
    private val TAG: String = "KizuHachimonFragment"
    private var _binding: FragmentKizuHachimonBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val mUtil: Utility = Utility()
    private lateinit var mContext: Context

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variables of Kizu and Hachimon chart layout */
    private var mGogyouContainerWidth: Int = 0
    private var mContainterPartitionValue: Int = 0

    /** Variables of energy scores */
    private var mKanScoreList: IntArray = IntArray(10)
    private var mMokuScore: String = ""
    private var mKaScore: String = ""
    private var mDoScore: String = ""
    private var mGonScore: String = ""
    private var mSuiScore: String = ""

    /**
     * Create kizuhachimon fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create kizuhachimon fragment")
    }

    /**
     * Create kizuhachimon fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKizuHachimonBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: kizuhachimon fragment view create")

        return binding.root
    }

    /**
     * Created kizuhachimon fragment view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: kizuhachimon fragment view created")

        mContext = requireContext()
        mGogyouContainerWidth = mContext.resources.getDimension(R.dimen.kizu_hachimon_container_size).toInt()
        mContainterPartitionValue = mContext.resources.getInteger(R.integer.kizu_hachimon_container_partition)
        binding.kizuHachimonKizuChart.kizuContainer.post {
            adjustKizuChartLayout()
        }
        binding.kizuHachimonHachimonChart.hachimonContainer.post {
            adjustHachimonChartLayout()
        }

        setScore()
        setKizuChart()
        setHachimonChart()
        setKanTable()
    }

    /**
     * Destroy kizuhachimon fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: kizuhachimon fragment view destroyed")
    }

    /**
     * Programmatically adjust the layout of the Kizu-hou
     */
    private fun adjustKizuChartLayout() {
        var kizuContainerWidth = binding.kizuHachimonKizuChart.kizuContainer.width
        val kizuContainerHeight = binding.kizuHachimonKizuChart.kizuContainer.height
        if (kizuContainerWidth > kizuContainerHeight)
            kizuContainerWidth = kizuContainerHeight
        val layoutParam = binding.kizuHachimonKizuChart.kizuContainer.layoutParams
        layoutParam.width = kizuContainerWidth
        layoutParam.height = kizuContainerWidth
        binding.kizuHachimonKizuChart.kizuContainer.layoutParams = layoutParam

        if (kizuContainerWidth < mGogyouContainerWidth * mContainterPartitionValue) {
            val layoutParamArray = arrayOf(
                binding.kizuHachimonKizuChart.kizuMokuContainer.layoutParams,
                binding.kizuHachimonKizuChart.kizuKaContainer.layoutParams,
                binding.kizuHachimonKizuChart.kizuDoContainer.layoutParams,
                binding.kizuHachimonKizuChart.kizuGonContainer.layoutParams,
                binding.kizuHachimonKizuChart.kizuSuiContainer.layoutParams
            )
            for ((index, param) in layoutParamArray.withIndex()) {
                param.width = kizuContainerWidth.div(mContainterPartitionValue)
                param.height = kizuContainerWidth.div(mContainterPartitionValue)
                when (index) {
                    0 -> binding.kizuHachimonKizuChart.kizuMokuContainer.layoutParams = param
                    1 -> binding.kizuHachimonKizuChart.kizuKaContainer.layoutParams = param
                    2 -> binding.kizuHachimonKizuChart.kizuDoContainer.layoutParams = param
                    3 -> binding.kizuHachimonKizuChart.kizuGonContainer.layoutParams = param
                    4 -> binding.kizuHachimonKizuChart.kizuSuiContainer.layoutParams = param
                }
            }
        }
    }

    /**
     * Programmatically adjust the layout of the Hachimon-hou
     */
    private fun adjustHachimonChartLayout() {
        var hachimonContainerWidth = binding.kizuHachimonHachimonChart.hachimonContainer.width
        val hachimonContainerHeight = binding.kizuHachimonHachimonChart.hachimonContainer.height
        if (hachimonContainerWidth > hachimonContainerHeight)
            hachimonContainerWidth = hachimonContainerHeight
        val layoutParam = binding.kizuHachimonHachimonChart.hachimonContainer.layoutParams
        layoutParam.width = hachimonContainerWidth
        layoutParam.height = hachimonContainerWidth
        binding.kizuHachimonHachimonChart.hachimonContainer.layoutParams = layoutParam

        if (hachimonContainerWidth < mGogyouContainerWidth * mContainterPartitionValue) {
            val layoutParamArray = arrayOf(
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.layoutParams,
                binding.kizuHachimonHachimonChart.hachimonWestContainer.layoutParams,
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.layoutParams,
                binding.kizuHachimonHachimonChart.hachimonEastContainer.layoutParams,
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.layoutParams
            )
            for ((index, param) in layoutParamArray.withIndex()) {
                param.width = hachimonContainerWidth.div(mContainterPartitionValue)
                param.height = hachimonContainerWidth.div(mContainterPartitionValue)
                when (index) {
                    0 -> binding.kizuHachimonHachimonChart.hachimonNorthContainer.layoutParams = param
                    1 -> binding.kizuHachimonHachimonChart.hachimonWestContainer.layoutParams = param
                    2 -> binding.kizuHachimonHachimonChart.hachimonCenterContainer.layoutParams = param
                    3 -> binding.kizuHachimonHachimonChart.hachimonEastContainer.layoutParams = param
                    4 -> binding.kizuHachimonHachimonChart.hachimonSouthContainer.layoutParams = param
                }
            }
        }
    }

    /**
     * Set energy scores for each of gogyou type and kan type
     */
    private fun setScore() {
        val arrayNo = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val kanList = IntArray(10)

        kanList[mYearKanNo - 1]++
        kanList[mMonthKanNo - 1]++
        kanList[mDayKanNo - 1]++
        for (i in arrayNo) {
            val shoNo = ZouKan.valueOf("ZouKan$i").shoNo
            val chuNo = ZouKan.valueOf("ZouKan$i").chuNo
            val honNo = ZouKan.valueOf("ZouKan$i").honNo
            if (shoNo > 0)
                kanList[shoNo - 1]++
            if (chuNo > 0)
                kanList[chuNo - 1]++
            if (honNo > 0)
                kanList[honNo - 1]++
        }

        for (i in arrayNo) {
            for ((index, j) in kanList.withIndex()) {
                mKanScoreList[index] = mKanScoreList[index] + j * mUtil.getEnergyScore(index + 1, i)
            }
        }
        mMokuScore = (mKanScoreList[0] + mKanScoreList[1]).toString()
        mKaScore = (mKanScoreList[2] + mKanScoreList[3]).toString()
        mDoScore = (mKanScoreList[4] + mKanScoreList[5]).toString()
        mGonScore = (mKanScoreList[6] + mKanScoreList[7]).toString()
        mSuiScore = (mKanScoreList[8] + mKanScoreList[9]).toString()
    }

    /**
     * Set Kizu chart scores
     */
    private fun setKizuChart() {
        binding.kizuHachimonKizuChart.kizuMokuScore.text = mMokuScore
        binding.kizuHachimonKizuChart.kizuKaScore.text = mKaScore
        binding.kizuHachimonKizuChart.kizuDoScore.text = mDoScore
        binding.kizuHachimonKizuChart.kizuGonScore.text = mGonScore
        binding.kizuHachimonKizuChart.kizuSuiScore.text = mSuiScore

    }

    /**
     * Set Hachimon chart scores
     */
    private fun setHachimonChart() {
        val mokuColor = resources.getColor(R.color.gogyou_moku, null)
        val kaColor = resources.getColor(R.color.gogyou_ka, null)
        val doColor = resources.getColor(R.color.gogyou_do, null)
        val gonColor = resources.getColor(R.color.gogyou_gon, null)
        val suiColor = resources.getColor(R.color.gogyou_sui, null)
        when (mDayKanNo) {
            1, 2 -> {
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.setBackgroundColor(suiColor)
                binding.kizuHachimonHachimonChart.hachimonWestContainer.setBackgroundColor(gonColor)
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.setBackgroundColor(mokuColor)
                binding.kizuHachimonHachimonChart.hachimonEastContainer.setBackgroundColor(doColor)
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.setBackgroundColor(kaColor)
                binding.kizuHachimonHachimonChart.hachimonNorthText.text = getString(R.string.common_sui_text)
                binding.kizuHachimonHachimonChart.hachimonWestText.text = getString(R.string.common_gon_text)
                binding.kizuHachimonHachimonChart.hachimonCenterText.text = getString(R.string.common_moku_text)
                binding.kizuHachimonHachimonChart.hachimonEastText.text = getString(R.string.common_do_text)
                binding.kizuHachimonHachimonChart.hachimonSouthText.text = getString(R.string.common_ka_text)
                binding.kizuHachimonHachimonChart.hachimonNorthScore.text = mSuiScore
                binding.kizuHachimonHachimonChart.hachimonWestScore.text = mGonScore
                binding.kizuHachimonHachimonChart.hachimonCenterScore.text = mMokuScore
                binding.kizuHachimonHachimonChart.hachimonEastScore.text = mDoScore
                binding.kizuHachimonHachimonChart.hachimonSouthScore.text = mKaScore
            }
            3, 4 -> {
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.setBackgroundColor(mokuColor)
                binding.kizuHachimonHachimonChart.hachimonWestContainer.setBackgroundColor(suiColor)
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.setBackgroundColor(kaColor)
                binding.kizuHachimonHachimonChart.hachimonEastContainer.setBackgroundColor(gonColor)
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.setBackgroundColor(doColor)
                binding.kizuHachimonHachimonChart.hachimonNorthText.text = getString(R.string.common_moku_text)
                binding.kizuHachimonHachimonChart.hachimonWestText.text = getString(R.string.common_sui_text)
                binding.kizuHachimonHachimonChart.hachimonCenterText.text = getString(R.string.common_ka_text)
                binding.kizuHachimonHachimonChart.hachimonEastText.text = getString(R.string.common_gon_text)
                binding.kizuHachimonHachimonChart.hachimonSouthText.text = getString(R.string.common_do_text)
                binding.kizuHachimonHachimonChart.hachimonNorthScore.text = mMokuScore
                binding.kizuHachimonHachimonChart.hachimonWestScore.text = mSuiScore
                binding.kizuHachimonHachimonChart.hachimonCenterScore.text = mKaScore
                binding.kizuHachimonHachimonChart.hachimonEastScore.text = mGonScore
                binding.kizuHachimonHachimonChart.hachimonSouthScore.text = mDoScore
            }
            5, 6 -> {
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.setBackgroundColor(kaColor)
                binding.kizuHachimonHachimonChart.hachimonWestContainer.setBackgroundColor(mokuColor)
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.setBackgroundColor(doColor)
                binding.kizuHachimonHachimonChart.hachimonEastContainer.setBackgroundColor(suiColor)
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.setBackgroundColor(gonColor)
                binding.kizuHachimonHachimonChart.hachimonNorthText.text = getString(R.string.common_ka_text)
                binding.kizuHachimonHachimonChart.hachimonWestText.text = getString(R.string.common_moku_text)
                binding.kizuHachimonHachimonChart.hachimonCenterText.text = getString(R.string.common_do_text)
                binding.kizuHachimonHachimonChart.hachimonEastText.text = getString(R.string.common_sui_text)
                binding.kizuHachimonHachimonChart.hachimonSouthText.text = getString(R.string.common_gon_text)
                binding.kizuHachimonHachimonChart.hachimonNorthScore.text = mKaScore
                binding.kizuHachimonHachimonChart.hachimonWestScore.text = mMokuScore
                binding.kizuHachimonHachimonChart.hachimonCenterScore.text = mDoScore
                binding.kizuHachimonHachimonChart.hachimonEastScore.text = mSuiScore
                binding.kizuHachimonHachimonChart.hachimonSouthScore.text = mGonScore
            }
            7, 8 -> {
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.setBackgroundColor(doColor)
                binding.kizuHachimonHachimonChart.hachimonWestContainer.setBackgroundColor(kaColor)
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.setBackgroundColor(gonColor)
                binding.kizuHachimonHachimonChart.hachimonEastContainer.setBackgroundColor(mokuColor)
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.setBackgroundColor(suiColor)
                binding.kizuHachimonHachimonChart.hachimonNorthText.text = getString(R.string.common_do_text)
                binding.kizuHachimonHachimonChart.hachimonWestText.text = getString(R.string.common_ka_text)
                binding.kizuHachimonHachimonChart.hachimonCenterText.text = getString(R.string.common_gon_text)
                binding.kizuHachimonHachimonChart.hachimonEastText.text = getString(R.string.common_moku_text)
                binding.kizuHachimonHachimonChart.hachimonSouthText.text = getString(R.string.common_sui_text)
                binding.kizuHachimonHachimonChart.hachimonNorthScore.text = mDoScore
                binding.kizuHachimonHachimonChart.hachimonWestScore.text = mKaScore
                binding.kizuHachimonHachimonChart.hachimonCenterScore.text = mGonScore
                binding.kizuHachimonHachimonChart.hachimonEastScore.text = mMokuScore
                binding.kizuHachimonHachimonChart.hachimonSouthScore.text = mSuiScore
            }
            9, 10 -> {
                binding.kizuHachimonHachimonChart.hachimonNorthContainer.setBackgroundColor(gonColor)
                binding.kizuHachimonHachimonChart.hachimonWestContainer.setBackgroundColor(doColor)
                binding.kizuHachimonHachimonChart.hachimonCenterContainer.setBackgroundColor(suiColor)
                binding.kizuHachimonHachimonChart.hachimonEastContainer.setBackgroundColor(kaColor)
                binding.kizuHachimonHachimonChart.hachimonSouthContainer.setBackgroundColor(mokuColor)
                binding.kizuHachimonHachimonChart.hachimonNorthText.text = getString(R.string.common_gon_text)
                binding.kizuHachimonHachimonChart.hachimonWestText.text = getString(R.string.common_do_text)
                binding.kizuHachimonHachimonChart.hachimonCenterText.text = getString(R.string.common_sui_text)
                binding.kizuHachimonHachimonChart.hachimonEastText.text = getString(R.string.common_ka_text)
                binding.kizuHachimonHachimonChart.hachimonSouthText.text = getString(R.string.common_moku_text)
                binding.kizuHachimonHachimonChart.hachimonNorthScore.text = mGonScore
                binding.kizuHachimonHachimonChart.hachimonWestScore.text = mDoScore
                binding.kizuHachimonHachimonChart.hachimonCenterScore.text = mSuiScore
                binding.kizuHachimonHachimonChart.hachimonEastScore.text = mKaScore
                binding.kizuHachimonHachimonChart.hachimonSouthScore.text = mMokuScore
            }
        }
    }

    /**
     * Set Hachimon kan table scores
     */
    private fun setKanTable() {
        binding.kizuHachimonScoreTable.mokuScore.text = mMokuScore
        binding.kizuHachimonScoreTable.kaScore.text = mKaScore
        binding.kizuHachimonScoreTable.doScore.text = mDoScore
        binding.kizuHachimonScoreTable.gonScore.text = mGonScore
        binding.kizuHachimonScoreTable.suiScore.text = mSuiScore

        binding.kizuHachimonScoreTable.koubokuScore.text = mKanScoreList[0].toString()
        binding.kizuHachimonScoreTable.otsubokuScore.text = mKanScoreList[1].toString()
        binding.kizuHachimonScoreTable.heikaScore.text = mKanScoreList[2].toString()
        binding.kizuHachimonScoreTable.teikaScore.text = mKanScoreList[3].toString()
        binding.kizuHachimonScoreTable.bodoScore.text = mKanScoreList[4].toString()
        binding.kizuHachimonScoreTable.kidoScore.text = mKanScoreList[5].toString()
        binding.kizuHachimonScoreTable.koukinScore.text = mKanScoreList[6].toString()
        binding.kizuHachimonScoreTable.shinkinScore.text = mKanScoreList[7].toString()
        binding.kizuHachimonScoreTable.jinsuiScore.text = mKanScoreList[8].toString()
        binding.kizuHachimonScoreTable.kisuiScore.text = mKanScoreList[9].toString()

        binding.kizuHachimonScoreTable.totalScore.text = mKanScoreList.sum().toString()
    }
}