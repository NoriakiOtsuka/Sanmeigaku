package com.example.sanmeigaku

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sanmeigaku.Adapter.GogyouScoreAdapter
import com.example.sanmeigaku.Adapter.JukkanScoreAdapter
import com.example.sanmeigaku.Enum.ZouKan
import com.example.sanmeigaku.Util.Utility
import com.example.sanmeigaku.View.SuuriGraphMarkerView
import com.example.sanmeigaku.databinding.FragmentSuuriBinding
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlin.math.abs
import kotlin.math.roundToInt

class SuuriFragment : Fragment() {
    private val TAG: String = "SuuriFragment"
    private var _binding: FragmentSuuriBinding? = null
    private val binding get() = _binding!!
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variable of the difference from the beginning of the month to the birthday received from the assessment activity */
    private val mFatalOrder: Int = activity.mFatalOrder
    private val mTaiStartAge: Int = activity.mTaiStartAge
    private val mTaiKanNo: Int = activity.mTaiKanNo
    private val mTaiShiNo: Int = activity.mTaiShiNo

    /** Array to store scores for each gogyou and jukkan table */
    private val mBaseRowScoreList = Array(sRowNum) {IntArray(10)}
    private var mGogyouScoreList = Array(sRowNum) {FloatArray(9)}
    private var mJukkanScoreList = Array(sRowNum) {IntArray(10)}
    private val mBaseAveScoreArray = IntArray(10)

    /** Data set for each item to be plotted on a line graph */
    private var mMokuScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mKaScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mDoScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mGonScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mSuiScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mDiffNumDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mDisplacementDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mCapacityRateDataSet:LineDataSet = LineDataSet(mutableListOf(), "")
    private var mTotalScoreDataSet:LineDataSet = LineDataSet(mutableListOf(), "")

    /** Status of the buttons related to the graph */
    private var mGogyouGraphMode: Boolean = true
    private var mIsGraphMoku: Boolean = false
    private var mIsGraphKa: Boolean = false
    private var mIsGraphDo: Boolean = false
    private var mIsGraphGon: Boolean = false
    private var mIsGraphSui: Boolean = false
    private var mIsGraphDiffNum: Boolean = false
    private var mIsGraphDisplacement: Boolean = false

    companion object {
        /** Number of rows in table */
        const val sRowNum = 101
    }

    /**
     * Create suuri fragment
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: create suuri fragment")
    }

    /**
     * Create suuri fragment view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuuriBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: suuri fragment view create")
        return binding.root
    }

    /**
     * Created suuri fragment view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: suuri fragment view created")

        calculateScore()
        setGogyouTable()
        setJukkanTable()
        toggleButtonActive()
        setScoreLineChartData()

        binding.gogyouButton.setOnClickListener {
            Log.i(TAG, "onViewCreated gogyouButton: mGogyouGraphMode was $mGogyouGraphMode before being tapped.")
            if (!mGogyouGraphMode) {
                mGogyouGraphMode = true
                binding.gogyouScoreTable.gogyouScoreTable.isVisible = true
                binding.jukkanScoreTable.jukkanScoreTable.isVisible = false
                toggleButtonActive()
                setScoreLineChart()
            }
        }

        binding.jukkanButton.setOnClickListener {
            Log.i(TAG, "onViewCreated jukkanButton: mGogyouGraphMode was $mGogyouGraphMode before being tapped." )
            if (mGogyouGraphMode) {
                mGogyouGraphMode = false
                binding.gogyouScoreTable.gogyouScoreTable.isVisible = false
                binding.jukkanScoreTable.jukkanScoreTable.isVisible = true
                toggleButtonActive()
                setScoreLineChart()
            }
        }

        binding.mokuLineButton.setOnClickListener {
            mIsGraphMoku = !mIsGraphMoku
            if (mIsGraphMoku) {
                it.setBackgroundColor(resources.getColor(R.color.gogyou_moku, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_moku_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphMoku.")

            setScoreLineChart()
        }

        binding.kaLineButton.setOnClickListener {
            mIsGraphKa = !mIsGraphKa
            if (mIsGraphKa) {
                it.setBackgroundColor(resources.getColor(R.color.gogyou_ka, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_ka_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphKa.")

            setScoreLineChart()
        }

        binding.doLineButton.setOnClickListener {
            mIsGraphDo = !mIsGraphDo
            if (mIsGraphDo) {
                it.setBackgroundColor(resources.getColor(R.color.gogyou_do, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_do_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphDo.")

            setScoreLineChart()
        }

        binding.gonLineButton.setOnClickListener {
            mIsGraphGon = !mIsGraphGon
            if (mIsGraphGon) {
                it.setBackgroundColor(resources.getColor(R.color.gogyou_gon, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_gon_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphGon.")

            setScoreLineChart()
        }

        binding.suiLineButton.setOnClickListener {
            mIsGraphSui = !mIsGraphSui
            if (mIsGraphSui) {
                it.setBackgroundColor(resources.getColor(R.color.gogyou_sui, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_sui_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphSui.")

            setScoreLineChart()
        }

        binding.diffNumLineButton.setOnClickListener {
            mIsGraphDiffNum = !mIsGraphDiffNum
            if (mIsGraphDiffNum) {
                it.setBackgroundColor(resources.getColor(R.color.suuri_difference, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_difference_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphDiffNum.")

            setScoreLineChart()
        }

        binding.displacementLineButton.setOnClickListener {
            mIsGraphDisplacement = !mIsGraphDisplacement
            if (mIsGraphDisplacement) {
                it.setBackgroundColor(resources.getColor(R.color.suuri_displacement, null))
            } else {
                it.setBackgroundColor(resources.getColor(R.color.suuri_displacement_inactive, null))
            }
            Log.i(TAG, "onViewCreated mokuLineButton: button active mode is $mIsGraphDisplacement.")

            setScoreLineChart()
        }
    }

    /**
     * Destroy suuri fragment view
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: suuri fragment view destroyed")
    }

    /**
     * Calculate the number of points for each logarithm for each age and place them in the array
     */
    private fun calculateScore() {
        val util = Utility()

        for (i in 0 until sRowNum) {
            val kanNumList = setKanList(i)
            val shiNumList = setShiList(i)
            val rowsScoreList = IntArray(10)
            for ((indexKan, j) in kanNumList.withIndex()) {
                if (j > 0) {
                    for ((indexShi, k) in shiNumList.withIndex()) {
                        if (k > 0) {
                            rowsScoreList[indexKan] += j * k * util.getEnergyScore(indexKan + 1, indexShi + 1)
                        }
                    }
                }
            }

            mBaseRowScoreList[i] = rowsScoreList
        }

        for (rowList in mBaseRowScoreList) {
            for ((index, i) in rowList.withIndex()) {
                mBaseAveScoreArray[index] += i
            }
        }
    }

    /**
     * List by year how many of each of the jukkan are in the meishiki table
     */
    private fun setKanList(num: Int): IntArray {
        val kanNumList = IntArray(10)
        val kanList = IntArray(20)
        val taiKanNo =abs(mTaiKanNo + (1 - mFatalOrder) * 10 + mFatalOrder * (num - mTaiStartAge).floorDiv(10) - 1).rem(10) + 1
        val taiShiNo = abs(mTaiShiNo + (1 - mFatalOrder) * 12 + mFatalOrder * (num - mTaiStartAge).floorDiv(10) - 1).rem(12) + 1
        val thisYearKanNo = (mYearKanNo + num - 1).rem(10) + 1
        val thisYearShiNo = (mYearShiNo + num - 1).rem(12) + 1

        kanList[0] = mYearKanNo
        kanList[1] = mMonthKanNo
        kanList[2] = mDayKanNo
        kanList[3] = taiKanNo
        kanList[4] = thisYearKanNo
        kanList[5] = ZouKan.valueOf("ZouKan$mYearShiNo").shoNo
        kanList[6] = ZouKan.valueOf("ZouKan$mYearShiNo").chuNo
        kanList[7] = ZouKan.valueOf("ZouKan$mYearShiNo").honNo
        kanList[8] = ZouKan.valueOf("ZouKan$mMonthShiNo").shoNo
        kanList[9] = ZouKan.valueOf("ZouKan$mMonthShiNo").chuNo
        kanList[10] = ZouKan.valueOf("ZouKan$mMonthShiNo").honNo
        kanList[11] = ZouKan.valueOf("ZouKan$mDayShiNo").shoNo
        kanList[12] = ZouKan.valueOf("ZouKan$mDayShiNo").chuNo
        kanList[13] = ZouKan.valueOf("ZouKan$mDayShiNo").honNo
        kanList[14] = ZouKan.valueOf("ZouKan$taiShiNo").shoNo
        kanList[15] = ZouKan.valueOf("ZouKan$taiShiNo").chuNo
        kanList[16] = ZouKan.valueOf("ZouKan$taiShiNo").honNo
        kanList[17] = ZouKan.valueOf("ZouKan${thisYearShiNo}").shoNo
        kanList[18] = ZouKan.valueOf("ZouKan${thisYearShiNo}").chuNo
        kanList[19] = ZouKan.valueOf("ZouKan${thisYearShiNo}").honNo

        for (i in kanList) {
            if (i > 0)
                kanNumList[i - 1]++
        }

        return kanNumList
    }

    /**
     * List by year how many of each of the junishi are in the meishiki table
     */
    private fun setShiList(num: Int): IntArray {
        val shiNumList = IntArray(12)
        val shiList = IntArray(5)
        val taiShiNo = abs(mTaiShiNo + (1 - mFatalOrder) * 12 + mFatalOrder * (num - mTaiStartAge).floorDiv(10) - 1).rem(12) + 1
        val thisYearShiNo = (mYearShiNo + num - 1).rem(12) + 1

        shiList[0] = mYearShiNo
        shiList[1] = mMonthShiNo
        shiList[2] = mDayShiNo
        shiList[3] = taiShiNo
        shiList[4] = thisYearShiNo

        for (i in shiList) {
            if (i > 0)
                shiNumList[i - 1]++
        }

        return shiNumList
    }

    /**
     * Display calculated values in a gogyou table
     */
    private fun setGogyouTable() {
        setGogyouRowScore()
        setGogyouAverageScore()

        binding.gogyouScoreTable.gogyouScoreRows.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = GogyouScoreAdapter(mGogyouScoreList)
        }
    }

    /**
     * Set the calculated value for each column of the gogyou table
     */
    private fun setGogyouRowScore() {
        val gyouScoreList = Array(sRowNum) {IntArray(5)}
        var gyouSortedScoreArray: IntArray
        var diffNum = 0
        var displacement = 0
        var capacityRate = 0.0
        val gyouList = arrayOf(
            getString(R.string.common_moku_text),
            getString(R.string.common_ka_text),
            getString(R.string.common_do_text),
            getString(R.string.common_gon_text),
            getString(R.string.common_sui_text)
        )
        val gyouArray: Array<String?> = arrayOfNulls(5)

        for ((indexRow, i) in mBaseRowScoreList.withIndex()) {
            for ((indexColumn, j) in mBaseRowScoreList[indexRow].withIndex()) {
                gyouScoreList[indexRow][indexColumn.div(2)] += j
            }

            gyouSortedScoreArray = gyouScoreList[indexRow].sortedArray()
            diffNum = gyouSortedScoreArray[4] - gyouSortedScoreArray[0]
            displacement = 0
            capacityRate = (diffNum.toDouble() / gyouScoreList[indexRow].sum().toDouble() * 1000.0).roundToInt() / 1000.0

            var gyouMap = HashMap<String, Int>()
            for (j in gyouList.indices) {
                gyouMap.put(gyouList[j], gyouScoreList[indexRow][j])
            }
            gyouMap = gyouMap.entries.sortedBy { it.value }.associate { it.toPair() } as HashMap<String, Int>

            val tmpGyouArray = gyouMap.keys.toTypedArray()
            for (j in tmpGyouArray.indices) {
                if ((gyouArray[j] != null) && !gyouArray[j].equals(tmpGyouArray[j]))
                    displacement++
                gyouArray[j] = tmpGyouArray[j]
            }

            mGogyouScoreList[indexRow][0] = gyouScoreList[indexRow][0].toFloat()
            mGogyouScoreList[indexRow][1] = gyouScoreList[indexRow][1].toFloat()
            mGogyouScoreList[indexRow][2] = gyouScoreList[indexRow][2].toFloat()
            mGogyouScoreList[indexRow][3] = gyouScoreList[indexRow][3].toFloat()
            mGogyouScoreList[indexRow][4] = gyouScoreList[indexRow][4].toFloat()
            mGogyouScoreList[indexRow][5] = diffNum.toFloat()
            mGogyouScoreList[indexRow][6] = displacement.toFloat()
            mGogyouScoreList[indexRow][7] = capacityRate.toFloat()
            mGogyouScoreList[indexRow][8] = gyouScoreList[indexRow].sum().toFloat()
        }
    }

    /**
     * Set the average by summing each column of the gogyou table.
     */
    private fun setGogyouAverageScore() {
        val gyouAveScoreArray = IntArray(5)
        for ((index, i) in mBaseAveScoreArray.withIndex()) {
            gyouAveScoreArray[index.div(2)] += (i.toFloat() / sRowNum).roundToInt()
        }

        val gyouSortedScoreArray = gyouAveScoreArray.sortedArray()
        val diffNum = gyouSortedScoreArray[4] - gyouSortedScoreArray[0]
        val capacityRate = (diffNum.toDouble() / gyouAveScoreArray.sum().toDouble() * 1000.0).roundToInt() / 1000.0

        binding.gogyouScoreTable.mokuScore.text = (gyouAveScoreArray[0]).toString()
        binding.gogyouScoreTable.kaScore.text = (gyouAveScoreArray[1]).toString()
        binding.gogyouScoreTable.doScore.text = (gyouAveScoreArray[2]).toString()
        binding.gogyouScoreTable.gonScore.text = (gyouAveScoreArray[3]).toString()
        binding.gogyouScoreTable.suiScore.text = (gyouAveScoreArray[4]).toString()
        binding.gogyouScoreTable.differenceScore.text = diffNum.toString()
        binding.gogyouScoreTable.capacityScore.text = capacityRate.toString()
        binding.gogyouScoreTable.totalScore.text = gyouAveScoreArray.sum().toString()
    }

    /**
     * Display calculated values in a jukkan table
     */
    private fun setJukkanTable() {
        mJukkanScoreList = mBaseRowScoreList

        val kanAveScoreArray = IntArray(10)
        for ((index, i) in mBaseAveScoreArray.withIndex()) {
            kanAveScoreArray[index] = (i.toFloat() / sRowNum).roundToInt()
        }

        binding.jukkanScoreTable.koubokuScore.text = kanAveScoreArray[0].toString()
        binding.jukkanScoreTable.otsubokuScore.text = kanAveScoreArray[1].toString()
        binding.jukkanScoreTable.heikaScore.text = kanAveScoreArray[2].toString()
        binding.jukkanScoreTable.teikaScore.text = kanAveScoreArray[3].toString()
        binding.jukkanScoreTable.bodoScore.text = kanAveScoreArray[4].toString()
        binding.jukkanScoreTable.kidoScore.text = kanAveScoreArray[5].toString()
        binding.jukkanScoreTable.koukinScore.text = kanAveScoreArray[6].toString()
        binding.jukkanScoreTable.shinkinScore.text = kanAveScoreArray[7].toString()
        binding.jukkanScoreTable.jinsuiScore.text = kanAveScoreArray[8].toString()
        binding.jukkanScoreTable.kisuiScore.text = kanAveScoreArray[9].toString()
        binding.jukkanScoreTable.totalScore.text = kanAveScoreArray.sum().toString()

        binding.jukkanScoreTable.jukkanScoreRows.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = JukkanScoreAdapter(mJukkanScoreList)
        }
    }

    /**
     * Set up data to plot score values for each item on a line graph
     */
    private fun setScoreLineChartData() {
        Log.i(TAG, "setScoreLineChartData: Start to set up data")

        val mokuScoreList: MutableList<Entry> = mutableListOf()
        val kaScoreList: MutableList<Entry> = mutableListOf()
        val doScoreList: MutableList<Entry> = mutableListOf()
        val gonScoreList: MutableList<Entry> = mutableListOf()
        val suiScoreList: MutableList<Entry> = mutableListOf()
        val diffNumList: MutableList<Entry> = mutableListOf()
        val displacementList: MutableList<Entry> = mutableListOf()
        val capacityRateList: MutableList<Entry> = mutableListOf()
        val totalScoreList: MutableList<Entry> = mutableListOf()

        for (i in 0 until sRowNum) {
            mokuScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][0]))
            kaScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][1]))
            doScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][2]))
            gonScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][3]))
            suiScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][4]))
            diffNumList.add(Entry(i.toFloat(), mGogyouScoreList[i][5]))
            displacementList.add(Entry(i.toFloat(), mGogyouScoreList[i][6]))
            capacityRateList.add(Entry(i.toFloat(), mGogyouScoreList[i][7]))
            totalScoreList.add(Entry(i.toFloat(), mGogyouScoreList[i][8]))
        }

        mMokuScoreDataSet = LineDataSet(mokuScoreList, getString(R.string.common_moku_text))
        mMokuScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            setDrawCircles(false)
            color = resources.getColor(R.color.gogyou_moku, null)

            // Value
            valueTextColor = resources.getColor(R.color.gogyou_moku, null)
        }

        mKaScoreDataSet = LineDataSet(kaScoreList, getString(R.string.common_ka_text))
        mKaScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            setDrawCircles(false)
            color = resources.getColor(R.color.gogyou_ka, null)

            // Value
            valueTextColor = resources.getColor(R.color.gogyou_ka, null)
        }

        mDoScoreDataSet = LineDataSet(doScoreList, getString(R.string.common_do_text))
        mDoScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            setDrawCircles(false)
            color = resources.getColor(R.color.gogyou_do, null)

            // Value
            valueTextColor = resources.getColor(R.color.gogyou_do, null)
        }

        mGonScoreDataSet = LineDataSet(gonScoreList, getString(R.string.common_gon_text))
        mGonScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            setDrawCircles(false)
            color = resources.getColor(R.color.gogyou_gon, null)

            // Value
            valueTextColor = resources.getColor(R.color.gogyou_gon, null)
        }

        mSuiScoreDataSet = LineDataSet(suiScoreList, getString(R.string.common_sui_text))
        mSuiScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            setDrawCircles(false)
            color = resources.getColor(R.color.gogyou_sui, null)

            // Value
            valueTextColor = resources.getColor(R.color.gogyou_sui, null)
        }

        mDiffNumDataSet = LineDataSet(diffNumList, getString(R.string.suuri_scoretable_title_difference_text))
        mDiffNumDataSet.apply {
            // Axis (LEFT)

            // Line graph
            enableDashedLine(5f, 5f, 0f)
            setDrawCircles(false)
            color = resources.getColor(R.color.suuri_difference, null)

            // Value
            valueTextColor = resources.getColor(R.color.suuri_difference, null)
        }

        mDisplacementDataSet = LineDataSet(displacementList, getString(R.string.suuri_scoretable_title_displacement_text))
        mDisplacementDataSet.apply {
            // Axis (RIGHT)
            axisDependency = YAxis.AxisDependency.RIGHT

            // Line graph
            enableDashedLine(5f, 5f, 0f)
            setDrawCircles(false)
            color = resources.getColor(R.color.suuri_displacement, null)

            // Value
            valueTextColor = resources.getColor(R.color.suuri_displacement, null)
        }

        mCapacityRateDataSet = LineDataSet(capacityRateList, getString(R.string.suuri_scoretable_title_capacity_text))
        mCapacityRateDataSet.apply {
            // Axis (RIGHT)
            axisDependency = YAxis.AxisDependency.RIGHT

            // Line graph
            enableDashedLine(5f, 5f, 0f)
            setDrawCircles(false)
            color = resources.getColor(R.color.suuri_capacity, null)

            // Value
            valueFormatter = object: ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return "%.2f".format(value)
                }
            }
            valueTextColor = resources.getColor(R.color.dark_gray, null)
        }

        mTotalScoreDataSet = LineDataSet(totalScoreList, getString(R.string.suuri_scoretable_title_total_text))
        mTotalScoreDataSet.apply {
            // Axis (LEFT)

            // Line graph
            lineWidth = 1.5f
            circleRadius = 2f
            circleHoleRadius = 2f
            color = resources.getColor(R.color.suuri_total, null)
            setCircleColor(resources.getColor(R.color.suuri_total, null))

            // Value
            valueTextSize = 8f
            valueTextColor = resources.getColor(R.color.suuri_total, null)
        }

        setScoreLineChart()
    }

    /**
     * Settings for plotting the value of each item's score on a line graph
     */
    private fun setScoreLineChart() {
        Log.i(TAG, "setScoreLineChart: Start to set up graph")

        val lineDataSets = mutableListOf<ILineDataSet>()
        lineDataSets.add(mTotalScoreDataSet)
        lineDataSets.add(mCapacityRateDataSet)
        if (mGogyouGraphMode && mIsGraphMoku)
            lineDataSets.add(mMokuScoreDataSet)
        if (mGogyouGraphMode && mIsGraphKa)
            lineDataSets.add(mKaScoreDataSet)
        if (mGogyouGraphMode && mIsGraphDo)
            lineDataSets.add(mDoScoreDataSet)
        if (mGogyouGraphMode && mIsGraphGon)
            lineDataSets.add(mGonScoreDataSet)
        if (mGogyouGraphMode && mIsGraphSui)
            lineDataSets.add(mSuiScoreDataSet)
        if (mGogyouGraphMode && mIsGraphDiffNum)
            lineDataSets.add(mDiffNumDataSet)
        if (mGogyouGraphMode && mIsGraphDisplacement)
            lineDataSets.add(mDisplacementDataSet)

        val graphTextColor = resources.getColor(R.color.dark_gray, null)
        val markerView = SuuriGraphMarkerView(context, R.layout.suuri_graph_marker_view)
        markerView.chartView = binding.suuriLineGraph

        binding.suuriLineGraph.apply {
            clear()

            // Data
            data = LineData(lineDataSets)
            description.isEnabled = false

            // Axis
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                textColor = graphTextColor
            }
            axisLeft.textColor = graphTextColor
            axisRight.apply {
                isEnabled = true
                axisMinimum = 0f
                spaceTop = 100f
                textColor = graphTextColor
            }

            // Legend
            legend.form = Legend.LegendForm.LINE
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.textColor = graphTextColor

            // Marker
            marker = markerView
        }

        toggleButtonVisibility()
    }

    /**
     * Toggle between buttons gogyou and jukkan to activate one or the other.
     */
    private fun toggleButtonActive() {
        Log.i(TAG, "toggleButtonActive: mGogyouGraphMode is $mGogyouGraphMode")

        val activeColor = resources.getColor(R.color.active, null)
        val inactiveColor = resources.getColor(R.color.inactive, null)

        if (mGogyouGraphMode) {
            binding.gogyouButton.setBackgroundColor(activeColor)
            binding.jukkanButton.setBackgroundColor(inactiveColor)
        } else {
            binding.gogyouButton.setBackgroundColor(inactiveColor)
            binding.jukkanButton.setBackgroundColor(activeColor)
        }
    }

    /**
     * Toggles the visibility of the buttons associated with Graph gogyou.
     */
    private fun toggleButtonVisibility() {
        binding.mokuLineButton.isVisible = mGogyouGraphMode
        binding.kaLineButton.isVisible = mGogyouGraphMode
        binding.doLineButton.isVisible = mGogyouGraphMode
        binding.gonLineButton.isVisible = mGogyouGraphMode
        binding.suiLineButton.isVisible = mGogyouGraphMode
        binding.diffNumLineButton.isVisible = mGogyouGraphMode
        binding.displacementLineButton.isVisible = mGogyouGraphMode
    }
}