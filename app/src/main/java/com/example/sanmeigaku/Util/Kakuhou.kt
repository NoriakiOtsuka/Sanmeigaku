package com.example.sanmeigaku.Util

import android.util.Log
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.Enum.Kaku
import com.example.sanmeigaku.Enum.MainStar
import com.example.sanmeigaku.Enum.SecondStar

class Kakuhou {
    private val TAG: String = "Kakuhou"
    private val activity: AssessmentActivity.Companion = AssessmentActivity
    private val mUtil: Utility = Utility()
    private val mIsouUtil: Isouhou = Isouhou()

    /** Variables of kan-shi number received from the assessment activity */
    private val mDayKanShiNo: Int = activity.mDayKanShiNo
    private val mYearKanNo: Int = activity.mYearKanNo
    private val mYearShiNo: Int = activity.mYearShiNo
    private val mMonthKanNo: Int = activity.mMonthKanNo
    private val mMonthShiNo: Int = activity.mMonthShiNo
    private val mDayKanNo: Int = activity.mDayKanNo
    private val mDayShiNo: Int = activity.mDayShiNo

    /** Variables of zou-kan number received from the assessment activity */
    private val mYearZouKanNo: Int = activity.mYearZouKanNo
    private val mMonthZouKanNo: Int = activity.mMonthZouKanNo
    private val mDayZouKanNo: Int = activity.mDayZouKanNo

    /** Variables of main star number received from the assessment activity */
    private val mMainStar: Int = activity.mMainStarNo
    private val mMainStar1: Int = activity.mMainStar1No
    private val mMainStar2: Int = activity.mMainStar2No
    private val mMainStar3: Int = activity.mMainStar3No
    private val mMainStar4: Int = activity.mMainStar4No

    /** Variables of kan-shi gogyou type */
    private var mYearKanType: Int = 0
    private var mMonthKanType: Int = 0
    private var mDayKanType: Int = 0
    private var mYearShiType: Int = 0
    private var mMonthShiType: Int = 0
    private var mDayShiType: Int = 0

    /** Variables of zou-kan number based on shogen, chugen, hongen */
    private var mMonthZouKan1: Int = 0
    private var mMonthZouKan2: Int = 0
    private var mMonthZouKan3: Int = 0
    private var mZoukanStar1: Int = 0
    private var mZoukanStar2: Int = 0
    private var mZoukanStar3: Int = 0

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

        mMonthZouKan1 = mUtil.getZonKanNo(mMonthShiNo, 1)
        mMonthZouKan2 = mUtil.getZonKanNo(mMonthShiNo, 11)
        mMonthZouKan3 = mUtil.getZonKanNo(mMonthShiNo, 21)
        mZoukanStar1 = mUtil.getMainStarNo(mDayKanNo, mMonthZouKan1)
        mZoukanStar2 = mUtil.getMainStarNo(mDayKanNo, mMonthZouKan2)
        mZoukanStar3 = mUtil.getMainStarNo(mDayKanNo, mMonthZouKan3)

        Log.i(TAG, "setKakuhou: Calculate and set the appropriate Kaku")

        val array = intArrayOf(
            jusyouKaku(),
            juzaiKaku(),
            jusyouzaiKaku(),
            jukanKaku(),
            satsuinsousyouKaku(),
            syoukikanseiKaku(),
            henkikanseiKaku(),
            syoukizaiseiKaku(),
            henkizaiseiKaku(),
            syoukiinshinjuryoKaku(),
            henkiinshinjuryoKaku(),
            syokurokushinKaku(),
            syoukanKaku(),
            zakkizaiKaku(),
            zakkikanKaku(),
            zakkiinKaku(),
            zakkizaikanKaku(),
            zakkikaninKaku(),
            zakkizaiinKaku(),
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

    /** 6.正気官星格 */
    private fun syoukikanseiKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main8").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main1").id -> return result
                MainStar.valueOf("Main2").id -> return result
                MainStar.valueOf("Main3").id -> return result
                MainStar.valueOf("Main4").id -> return result
                MainStar.valueOf("Main7").id -> return result
                MainStar.valueOf("Main8").id -> mainStarCount++
            }
        }

        if ((mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo)) ||
            (mUtil.isNichizaTenchusatsu(mDayKanShiNo)))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        if (mainStarCount < 2) {
            result = 1
        } else {
            if (mainStarCount == 2)
                result = 0
            return result
        }

        val monthStarNo = mUtil.getSecondStarNo(mMonthZouKanNo, mMonthShiNo)
        if (monthStarNo == SecondStar.valueOf("Second6").id)
            result = 2

        return result
    }

    /** 7.偏気官星格 */
    private fun henkikanseiKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main7").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main3").id -> mainStarCount++
                MainStar.valueOf("Main4").id -> mainStarCount++
            }
        }

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        if ((mainStarCount == 0) &&
            (mMainStar1 == MainStar.valueOf("Main7").id)) {
            result = 4
        } else if (mainStarCount == 0) {
            result = 3
        } else if (mMainStar1 == MainStar.valueOf("Main7").id) {
            result = 2
        } else {
            result = 1
        }

        return result
    }

    /** 8.正気財星格 */
    private fun syoukizaiseiKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main6").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main1").id -> mainStarCount++
                MainStar.valueOf("Main2").id -> mainStarCount++
                MainStar.valueOf("Main5").id -> return result
            }
        }

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        when (mainStarCount) {
            0 -> result = 1
            1 -> result = 0
        }

        return result
    }

    /** 9.偏気財星格 */
    private fun henkizaiseiKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main5").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main1").id -> mainStarCount++
                MainStar.valueOf("Main2").id -> mainStarCount++
            }
        }

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        when (mainStarCount) {
            0 -> result = 1
            1 -> result = 0
        }

        return result
    }

    /** 10.正気印神綬了格 */
    private fun syoukiinshinjuryoKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main10").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarCount++
                MainStar.valueOf("Main6").id -> mainStarCount++
            }
        }

        val mainStarArray2 = arrayListOf(mMainStar2, mMainStar4)
        for (num in mainStarArray2) {
            when (num) {
                MainStar.valueOf("Main9").id -> return result
                MainStar.valueOf("Main10").id -> return result
            }
        }

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        if ((mUtil.getKangouNo(mDayKanNo, mYearKanNo) > 0) ||
            (mUtil.getKangouNo(mDayKanNo, mMonthKanNo) > 0))
            return result

        when (mainStarCount) {
            0 -> result = 1
            1 -> result = 0
        }

        return result
    }

    /** 11.偏気印神綬格 */
    private fun henkiinshinjuryoKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main9").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount1 = 0
        var mainStarCount2 = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarCount1++
                MainStar.valueOf("Main6").id -> mainStarCount1++
                MainStar.valueOf("Main9").id -> mainStarCount2++
                MainStar.valueOf("Main10").id -> mainStarCount2++
            }
        }

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        if (mainStarCount2 < 2) {
            when (mainStarCount1) {
                0 -> result = 1
                1 -> result = 0
            }
        }

        return result
    }

    /** 12.食禄神格 */
    private fun syokurokushinKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main3").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main3").id -> mainStarCount++
                MainStar.valueOf("Main4").id -> mainStarCount++
            }
        }
        if (mainStarCount > 1)
            return result

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        val yearStarNo = mUtil.getSecondStarNo(mYearZouKanNo, mYearShiNo)
        val monthStarNo = mUtil.getSecondStarNo(mMonthZouKanNo, mMonthShiNo)
        val dayStarNo = mUtil.getSecondStarNo(mDayZouKanNo, mDayShiNo)
        if (((mMainStar3 == MainStar.valueOf("Main3").id) && (yearStarNo == SecondStar.valueOf("Second6").id)) ||
            ((mMainStar == MainStar.valueOf("Main3").id) && (monthStarNo == SecondStar.valueOf("Second6").id)) ||
            ((mMainStar1 == MainStar.valueOf("Main3").id) && (dayStarNo == SecondStar.valueOf("Second6").id))) {
            result = 2
        } else {
            result = 1
        }

        return result
    }

    /** 13.傷官格 */
    private fun syoukanKaku(): Int {
        var result = -1

        if (mMainStar != MainStar.valueOf("Main4").id)
            return result

        val mainStarArray = arrayListOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        var mainStarCount1 = 0
        var mainStarCount2 = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarCount1++
                MainStar.valueOf("Main6").id -> mainStarCount1++
                MainStar.valueOf("Main9").id -> return result
                MainStar.valueOf("Main10").id -> mainStarCount2++
            }
        }
        if ((mainStarCount1 == 0) || (mainStarCount2 != 1))
            return result

        if (mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
            return result

        if ((mIsouUtil.getHankaiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHaNo(mDayShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getGaiNo(mDayShiNo, mMonthShiNo) > 0))
            return result

        result = 1

        return result
    }

    /** 14.雑気財格 */
    private fun zakkizaiKaku(): Int {
        var result =-1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarCount++
                MainStar.valueOf("Main6").id -> mainStarCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> zoukanStarCount++
                MainStar.valueOf("Main6").id -> zoukanStarCount++
            }
        }

        if ((mainStarCount == 0) || (zoukanStarCount == 0))
            return result

        result = 1

        return result
    }

    /** 15.雑気官格 */
    private fun zakkikanKaku(): Int {
        var result = -1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main7").id -> mainStarCount++
                MainStar.valueOf("Main8").id -> mainStarCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main7").id -> zoukanStarCount++
                MainStar.valueOf("Main8").id -> zoukanStarCount++
            }
        }

        if ((mainStarCount == 0) || (zoukanStarCount == 0))
            return result

        result = 1

        return result
    }

    /** 16.雑気印格 */
    private fun zakkiinKaku(): Int {
        var result = -1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main9").id -> mainStarCount++
                MainStar.valueOf("Main10").id -> mainStarCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main9").id -> zoukanStarCount++
                MainStar.valueOf("Main10").id -> zoukanStarCount++
            }
        }

        if ((mainStarCount == 0) || (zoukanStarCount == 0))
            return result

        result = 1

        return result
    }

    /** 17.雑気財官格 */
    private fun zakkizaikanKaku(): Int {
        var result = -1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarZaiCount = 0
        var mainStarKanCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarZaiCount++
                MainStar.valueOf("Main6").id -> mainStarZaiCount++
                MainStar.valueOf("Main7").id -> mainStarKanCount++
                MainStar.valueOf("Main8").id -> mainStarKanCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarZaiCount = 0
        var zoukanStarKanCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> zoukanStarZaiCount++
                MainStar.valueOf("Main6").id -> zoukanStarZaiCount++
                MainStar.valueOf("Main7").id -> zoukanStarKanCount++
                MainStar.valueOf("Main8").id -> zoukanStarKanCount++
            }
        }

        if (((mainStarZaiCount == 0) || (mainStarKanCount == 0)) ||
            ((zoukanStarZaiCount == 0) || (zoukanStarKanCount == 0)))
            return result

        result = 1

        return result
    }

    /** 18.雑気官印格 */
    private fun zakkikaninKaku(): Int {
        var result = -1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarKanCount = 0
        var mainStarInCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main7").id -> mainStarKanCount++
                MainStar.valueOf("Main8").id -> mainStarKanCount++
                MainStar.valueOf("Main9").id -> mainStarInCount++
                MainStar.valueOf("Main10").id -> mainStarInCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarKanCount = 0
        var zoukanStarInCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main7").id -> zoukanStarKanCount++
                MainStar.valueOf("Main8").id -> zoukanStarKanCount++
                MainStar.valueOf("Main9").id -> zoukanStarInCount++
                MainStar.valueOf("Main10").id -> zoukanStarInCount++
            }
        }

        if (((mainStarKanCount == 0) || (mainStarInCount == 0)) ||
            ((zoukanStarKanCount == 0) || (zoukanStarInCount == 0)))
            return result

        result = 1

        return result
    }

    /** 19.雑気財印格 */
    private fun zakkizaiinKaku(): Int {
        var result = -1

        if (mMonthShiType != 3)
            return result

        val mainStarArray = arrayListOf(mMainStar2, mMainStar4)
        var mainStarZaiCount = 0
        var mainStarInCount = 0
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> mainStarZaiCount++
                MainStar.valueOf("Main6").id -> mainStarZaiCount++
                MainStar.valueOf("Main9").id -> mainStarInCount++
                MainStar.valueOf("Main10").id -> mainStarInCount++
            }
        }

        val zoukanStarArray = arrayListOf(mZoukanStar1, mZoukanStar2, mZoukanStar3)
        var zoukanStarZaiCount = 0
        var zoukanStarInCount = 0
        for (num in zoukanStarArray) {
            when (num) {
                MainStar.valueOf("Main5").id -> zoukanStarZaiCount++
                MainStar.valueOf("Main6").id -> zoukanStarZaiCount++
                MainStar.valueOf("Main9").id -> zoukanStarInCount++
                MainStar.valueOf("Main10").id -> zoukanStarInCount++
            }
        }

        if (((mainStarZaiCount == 0) || (mainStarInCount == 0)) ||
            ((zoukanStarZaiCount == 0) || (zoukanStarInCount == 0)))
            return result

        result = 1

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