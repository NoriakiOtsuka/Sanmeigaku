package com.example.sanmeigaku.Util

import android.util.Log
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.Enum.Kaku

class Kakuhou {
    private val TAG: String = "Kakuhou"
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of kan-shi number received from the assessment activity */
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variables of kan-shi gogyou type */
    private var mYearKanType: Int = 0
    private var mMonthKanType: Int = 0
    private var mDayKanType: Int = 0
    private var mYearShiType: Int = 0
    private var mMonthShiType: Int = 0
    private var mDayShiType: Int = 0

    /**
     * Set kakuhou items
     */
    fun setKakuhou(): String {
        mYearKanType = setGogyouFromKan(mYearKanNo)
        mMonthKanType = setGogyouFromKan(mMonthKanNo)
        mDayKanType = setGogyouFromKan(mDayKanNo)
        mYearShiType = setGogyouFromShi(mYearShiNo)
        mMonthShiType = setGogyouFromShi(mMonthShiNo)
        mDayShiType = setGogyouFromShi(mDayShiNo)

        Log.i(TAG, "setKakuhou: Calculate and set the appropriate Kaku")

        val array = intArrayOf(
            jusyouKaku(),
            juzaiKaku(),
            jusyouzaiKaku(),
            jukanKaku(),
            satsuinsousyouKaku()
        )
        var result = ""
        for ((index, i) in array.withIndex()) {
            if (i > -1)
                result += Kaku.valueOf("Kaku${index + 1}_$i").value + ", "
        }

        return result.dropLast(2)
    }

    /** 1.従生格 */
    private fun jusyouKaku(): Int {
        val param1 = (mDayKanType + 1).minus(1).rem(5).plus(1)
        var result = 1

        when (mDayKanType) {
            1, 2, 3, 4, 5 -> {
                if (mYearKanType != param1)
                    result--
                if (mMonthKanType != param1)
                    result--
                if (mYearShiType != param1)
                    result--
                if (mMonthShiType != param1)
                    result--
                if (mDayShiType != param1)
                    result--
            }
        }

        if (result < 0)
            return -1

        return result
    }

    /** 2.従財格 */
    private fun juzaiKaku(): Int {
        val param2 = (mDayKanType + 2).minus(1).rem(5).plus(1)
        var result = 1

        when (mDayKanType) {
            1, 2, 3, 4, 5 -> {
                if (mYearKanType != param2)
                    result--
                if (mMonthKanType != param2)
                    result--
                if (mYearShiType != param2)
                    result--
                if (mMonthShiType != param2)
                    result--
                if (mDayShiType != param2)
                    result--
            }
        }

        if (result < 0)
            return -1

        return result
    }

    /** 3.従生財格 */
    private fun jusyouzaiKaku(): Int {
        val param1 = (mDayKanType + 1).minus(1).rem(5).plus(1)
        val param2 = (mDayKanType + 2).minus(1).rem(5).plus(1)
        var result = 1

        when (mDayKanType) {
            1, 2, 3, 4, 5 -> {
                if (!((mYearKanType == param1) || (mYearKanType == param2)))
                    result--
                if (!((mMonthKanType == param1) || (mMonthKanType == param2)))
                    result--
                if (!((mYearShiType == param1) || (mYearShiType == param2)))
                    result--
                if (!((mMonthShiType == param1) || (mMonthShiType == param2)))
                    result--
                if (!((mDayShiType == param1) || (mDayShiType == param2)))
                    result--
            }
        }

        if (result < 0)
            return -1

        return result
    }

    /** 4.従官格 */
    private fun jukanKaku(): Int {
        val param3 = (mDayKanType + 3).minus(1).rem(5).plus(1)
        var result = 1

        when (mDayKanType) {
            1, 2, 3, 4, 5 -> {
                if (mYearKanType != param3)
                    result--
                if (mMonthKanType != param3)
                    result--
                if (mYearShiType != param3)
                    result--
                if (mMonthShiType != param3)
                    result--
                if (mDayShiType != param3)
                    result--
            }
        }

        if (result < 0)
            return -1

        return result
    }

    /** 5.殺印相生格 */
    private fun satsuinsousyouKaku(): Int {
        val param3 = (mDayKanType + 3).minus(1).rem(5).plus(1)
        val param4 = (mDayKanType + 4).minus(1).rem(5).plus(1)
        var result = 1

        when (mDayKanType) {
            1, 2, 3, 4, 5 -> {
                if (!((mYearKanType == param3) || (mYearKanType == param4)))
                    result--
                if (!((mMonthKanType == param3) || (mMonthKanType == param4)))
                    result--
                if (!((mYearShiType == param3) || (mYearShiType == param4)))
                    result--
                if (!((mMonthShiType == param3) || (mMonthShiType == param4)))
                    result--
                if (!((mDayShiType == param3) || (mDayShiType == param4)))
                    result--
            }
        }

        if (result < 0)
            return -1

        return result
    }

    /**
     * Set gogyou type of Kan
     */
    private fun setGogyouFromKan(num: Int): Int {
        var type = 0
        when (num) {
            1, 2 -> type = 1
            3, 4 -> type = 2
            5, 6 -> type = 3
            7, 8 -> type = 4
            9, 10 -> type = 5
            else -> {
                Log.e(TAG, "setGogyouFromKan: The variable \"type\" must not be $type.")
            }
        }

        return type
    }

    /**
     * Set gogyou type of Shi
     */
    private fun setGogyouFromShi(num: Int): Int {
        var type = 0
        when (num) {
            3, 4 -> type = 1
            6, 7 -> type = 2
            2, 5, 8, 11 -> type = 3
            9, 10 -> type = 4
            1, 12 -> type = 5
            else -> {
                Log.e(TAG, "setGogyouFromShi: The variable \"type\" must not be $type.")
            }
        }

        return type
    }
}