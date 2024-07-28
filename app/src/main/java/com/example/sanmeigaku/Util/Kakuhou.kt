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
    private val mYearKanShiNo: Int = activity.mYearKanShiNo
    private val mMonthKanShiNo: Int = activity.mMonthKanShiNo
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
            zinkiryuhaiKaku(),
            shiyoumiKaku(),
            heihitsuMeishiki(),
            hihitsuMeishiki(),
            rokkouintokuKaku(),
            rokkousuukenKaku(),
            syousyosyuuseiKaku(),
            kangoushikeiKaku(),
            hakanKaku(),
            hassenrokuouKaku(),
            tenkanrenjuKaku(),
            chishirenjuKaku(),
            tenchirenjuKaku(),
            kyokijusyouzaiKaku(),
            tenkansourenKaku(),
            yonchinkiKaku(),
            yonseiKaku(),
            yonshouKaku(),
            shigosouhouKaku(),
            seiransyaKaku1(),
            seiransyaKaku2(),
            ikkiseisyouKaku(),
            bosatsuKaku(),
            mokuseiTenkanikkiKaku(),
            kaseiTenkanikkiKaku(),
            doseiTenkanikkiKaku(),
            kinseiTenkanikkiKaku(),
            suiseiTenkanikkiKaku(),
            mokuseiChishiikkiKaku(),
            kaseiChishiikkiKaku(),
            doseiChishiikkiKaku(),
            kinseiChishiikkiKaku(),
            suiseiChishiikkiKaku(),
            mokuseiIkkiKaku(),
            kaseiIkkiKaku(),
            doseiIkkiKaku(),
            kinseiIkkiKaku(),
            suiseiIkkiKaku(),
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

    /** 20.壬騎龍背格 */
    private fun zinkiryuhaiKaku(): Int {
        var result = -1

        if (mDayKanShiNo != 29)
            return result

        val shiArray = arrayListOf(mYearShiNo, mMonthShiNo)
        var shiCount = 1
        for (i in shiArray) {
            when (i) {
                5 -> shiCount++
            }
        }

        if (shiCount == 3) {
            result = 2
        } else if (shiCount == 2) {
            result = 1
        }

        return result
    }

    /** 21.子遙巳格 */
    private fun shiyoumiKaku(): Int {
        var result = -1

        if ((mDayKanShiNo == 1) &&
            ((mYearKanShiNo == 1) || (mMonthKanShiNo == 1)))
            result = 1

        return result
    }

    /** 22.閉畢命式 */
    private fun heihitsuMeishiki(): Int {
        var result = -1

        when (mDayKanType) {
            in 1..5 -> {
                if (mYearKanType == (mDayKanType.rem(5) + 1)) {
                    if (!mUtil.isNanasatsu(mYearKanNo, mMonthKanNo))
                        return result
                } else if (mMonthKanType == (mDayKanType.rem(5) + 1)) {
                    if (!mUtil.isNanasatsu(mMonthKanNo, mYearKanNo))
                        return result
                } else {
                    return result
                }
            }
            else -> return result
        }

        val fRokuNo = mUtil.getRokushinNo(mDayKanNo).first
        val mRokuNo = mUtil.getRokushinNo(mDayKanNo).second
        if (!(((mYearKanNo == fRokuNo) && (mMonthKanNo == mRokuNo)) ||
                    ((mYearKanNo == mRokuNo) && (mMonthKanNo == fRokuNo))))
            return result

        val mainStar4Type = setGogyouFromKan(mMainStar4)
        val mainStarType = setGogyouFromKan(mMainStar)
        val mainStar2Type = setGogyouFromKan(mMainStar2)
        if (mainStar4Type == (mainStarType.rem(5) + 1))
            if (mainStarType == (mainStar2Type.rem(5) + 1))
                result = 1

        return result
    }

    /** 23.閟畢命式 */
    private fun hihitsuMeishiki(): Int {
        var result = -1

        when (mDayKanType) {
            in 1..5 -> {
                if (mYearKanType == (mDayKanType.rem(5) + 1)) {
                    if (mUtil.getKangouNo(mYearKanNo, mMonthKanNo) == 0)
                        return result
                } else if (mMonthKanType == (mDayKanType.rem(5) + 1)) {
                    if (mUtil.getKangouNo(mMonthKanNo, mYearKanNo) == 0)
                        return result
                } else {
                    return result
                }
            }
            else -> return result
        }

        val fRokuNo = mUtil.getRokushinNo(mDayKanNo).first
        val mRokuNo = mUtil.getRokushinNo(mDayKanNo).second
        if (!(((mYearKanNo == fRokuNo) && (mMonthKanNo == mRokuNo)) ||
                    ((mYearKanNo == mRokuNo) && (mMonthKanNo == fRokuNo))))
            return result

        val mainStar4Type = setGogyouFromKan(mMainStar4)
        val mainStarType = setGogyouFromKan(mMainStar)
        val mainStar2Type = setGogyouFromKan(mMainStar2)
        if (mainStar4Type == (mainStarType.rem(5) + 1))
            if (mainStarType == (mainStar2Type.rem(5) + 1))
                result = 1

        return result
    }

    /** 24.六甲印徳格 */
    private fun rokkouintokuKaku(): Int {
        var result = -1

        if (mDayKanNo != 1)
            return result

        if ((mYearShiNo == 12) || (mMonthShiNo == 12))
            result = 1

        return result
    }

    /** 25.六甲趨乾格 */
    private fun rokkousuukenKaku(): Int {
        var result = -1

        val shiNoArray = arrayListOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val boolArray = booleanArrayOf(false, false, false)
        for (i in shiNoArray) {
            when (i) {
                2 -> boolArray[0] = true
                6 -> boolArray[1] = true
                10 -> boolArray[2] = true
            }
        }

        if (boolArray.count { it } == 3)
            result = 1

        return result
    }

    /** 26.生処集生格 */
    private fun syousyosyuuseiKaku(): Int {
        var result = -1

        val secondStar1 = mUtil.getSecondStarNo(mDayKanNo, mDayShiNo)
        if (((mMainStar2 == MainStar.valueOf("Main10").id) ||
                    (mMainStar4 == MainStar.valueOf("Main10").id)) &&
            (secondStar1 == SecondStar.valueOf("Second3").id))
            result = 1

        return result
    }

    /** 27.干合支刑格 */
    private fun kangoushikeiKaku(): Int {
        var result = -1

        if (mUtil.getKangouNo(mDayKanNo, mYearKanNo) > 0)
            if (mIsouUtil.getKeiNo(mDayShiNo, mYearShiNo) > 0)
                result = 1

        if (mUtil.getKangouNo(mDayKanNo, mMonthKanNo) > 0)
            if (mIsouUtil.getKeiNo(mDayShiNo, mMonthShiNo) > 0)
                result = 1

        return result
    }

    /** 28.破官格 */
    private fun hakanKaku(): Int {
        var result = -1

        val mainStarArray = arrayListOf(mMainStar, mMainStar1, mMainStar2, mMainStar3, mMainStar4)
        for (num in mainStarArray) {
            when (num) {
                MainStar.valueOf("Main3").id -> return result
                MainStar.valueOf("Main5").id -> return result
                MainStar.valueOf("Main6").id -> return result
                MainStar.valueOf("Main8").id -> return result
                MainStar.valueOf("Main10").id -> return result
            }
        }

        if ((mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo) > 0) ||
            (mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo) > 0))
            return result

        if ((mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo) > 0) ||
            (mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo) > 0))
            return result

        if (((mIsouUtil.getHaNo(mYearShiNo, mMonthShiNo) > 0) ||
                    (mIsouUtil.getHaNo(mMonthShiNo, mDayShiNo) > 0) ||
                    (mIsouUtil.getHaNo(mDayShiNo, mYearShiNo) > 0)) &&
            ((mIsouUtil.getGaiNo(mYearShiNo, mMonthShiNo) > 0) ||
                    (mIsouUtil.getGaiNo(mMonthShiNo, mDayShiNo) > 0) ||
                    (mIsouUtil.getGaiNo(mDayShiNo, mYearShiNo) > 0)))
            result = 1

        return result
    }

    /** 29.八専禄旺格 */
    private fun hassenrokuouKaku(): Int {
        var result = -1

        if ((mDayKanShiNo == 51) || (mDayKanShiNo == 52) || (mDayKanShiNo == 57) || (mDayKanShiNo == 58))
            result = 1

        return result
    }

    /** 30.天干連珠格 */
    private fun tenkanrenjuKaku(): Int {
        var result = -1

        when (mYearKanType) {
            1 -> when (mMonthKanType) {
                1 -> when (mDayKanType) {
                    2, 5 -> result = 0
                }
                2 -> when (mDayKanType) {
                    2 -> result = 0
                    3 -> result = 1
                }
                5 -> when (mDayKanType) {
                    4 -> result = 1
                    5 -> result = 0
                }
            }
            2 -> when (mMonthKanType) {
                1 -> when (mDayKanType) {
                    1 -> result = 0
                    5 -> result = 1
                }
                2 -> when (mDayKanType) {
                    1, 3 -> result = 0
                }
                3 -> when (mDayKanType) {
                    3 -> result = 0
                    4 -> result = 1
                }
            }
            3 -> when (mMonthKanType) {
                2 -> when (mDayKanType) {
                    1 -> result = 1
                    2 -> result = 0
                }
                3 -> when (mDayKanType) {
                    2, 4 -> result = 0
                }
                4 -> when (mDayKanType) {
                    4 -> result = 0
                    5 -> result = 1
                }
            }
            4 -> when (mMonthKanType) {
                3 -> when (mDayKanType) {
                    2 -> result = 1
                    3 -> result = 0
                }
                4 -> when (mDayKanType) {
                    3, 5 -> result = 0
                }
                5 -> when (mDayKanType) {
                    1 -> result = 1
                    5 -> result = 0
                }
            }
            5 -> when (mMonthKanType) {
                1 -> when (mDayKanType) {
                    1 -> result = 0
                    2 -> result = 1
                }
                4 -> when (mDayKanType) {
                    3 -> result = 1
                    4 -> result = 0
                }
                5 -> when (mDayKanType) {
                    1, 4 -> result = 0
                }
            }
        }

        if (result == 1) {
            if ((mUtil.getKangouNo(mYearKanNo, mMonthKanNo) > 0) ||
                (mUtil.getKangouNo(mMonthKanNo, mDayKanNo) > 0) ||
                (mUtil.getKangouNo(mDayKanNo, mYearKanNo) > 0))
                result = 0

            if (mUtil.isSeinenTenchusatsu(mDayKanShiNo, mYearShiNo) ||
                mUtil.isSeigetsuTenchusatsu(mDayKanShiNo, mMonthShiNo))
                result = 0
        }

        return result
    }

    /** 31.地支連珠格 */
    private fun chishirenjuKaku(): Int {
        var result = -1

        if ((mMonthShiNo == (mYearShiNo.rem(12) + 1)) &&
            (mDayShiNo == (mMonthShiNo.rem(12) + 1)))
            result = 1

        if ((mYearShiNo == (mMonthShiNo.rem(12) + 1)) &&
            (mMonthShiNo == (mDayShiNo.rem(12) + 1)))
            result = 1

        if ((mMonthShiNo == (mYearShiNo.plus(1).rem(12).minus(1) + 2)) &&
            (mDayShiNo == (mMonthShiNo.plus(1).rem(12).minus(1) + 2)))
            result = 1

        if ((mYearShiNo == (mMonthShiNo.plus(1).rem(12).minus(1) + 2)) &&
            (mMonthShiNo == (mDayShiNo.plus(1).rem(12).minus(1) + 2)))
            result = 1

        return result
    }

    /** 32.天地連珠格 */
    private fun tenchirenjuKaku(): Int {
        var result = -1

        if (chishirenjuKaku() == -1)
            return -1

        when (tenkanrenjuKaku()) {
            0 -> result = 0
            1 -> result = 1
        }

        return result
    }

    /** 33.虚気従生財格 */
    private fun kyokijusyouzaiKaku(): Int {
        var result = -1

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if (kangouNo == 0)
            return -1

        val kanType = setGogyouFromKan(kangouNo)
        if (kanType != (mDayKanType.plus(1).rem(5).minus(1) + 2))
            return -1

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if (((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) &&
            ((shigouNo1 == 0) && (shigouNo2 == 0) && (shigouNo3 == 0)))
            return -1

        if ((hankaiNo1 != 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if ((mDayShiType == mDayKanType.rem(5) + 1) &&
                (hankaiNo1 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        } else if ((hankaiNo1 == 0) && (hankaiNo2 != 0) && (hankaiNo3 == 0)) {
            if ((mYearShiType == mDayKanType.rem(5) + 1) &&
                (hankaiNo2 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        } else if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 != 0)) {
            if ((mMonthShiType == mDayKanType.rem(5) + 1) &&
                (hankaiNo3 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        } else if ((shigouNo1 != 0) && (shigouNo2 == 0) && (shigouNo3 == 0)) {
            if ((mDayShiType == mDayKanType.rem(5) + 1) &&
                (shigouNo1 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        } else if ((shigouNo1 == 0) && (shigouNo2 != 0) && (shigouNo3 == 0)) {
            if ((mYearShiType == mDayKanType.rem(5) + 1) &&
                (shigouNo2 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        } else if ((shigouNo1 == 0) && (shigouNo2 == 0) && (shigouNo3 != 0)) {
            if ((mMonthShiType == mDayKanType.rem(5) + 1) &&
                (shigouNo3 == mDayKanType.plus(1).rem(5).minus(1) + 2))
                result = 1
        }

        return result
    }

    /** 34.天干双連格 */
    private fun tenkansourenKaku(): Int {
        var result = -1

        if ((mUtil.getKangouNo(mYearKanNo, mMonthKanNo) > 0) || (mYearKanType == mMonthKanType))
            if (mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo) > 0)
                result = 1

        if ((mUtil.getKangouNo(mDayKanNo, mYearKanNo) > 0) || (mDayKanType == mYearKanType))
            if (mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo) > 0)
                result = 2

        if ((mUtil.getKangouNo(mDayKanNo, mMonthKanNo) > 0) || (mDayKanType == mMonthKanType))
            if (mIsouUtil.getShigouNo(mDayShiNo, mMonthShiNo) > 0)
                result = 2

        return result
    }

    /** 35.四鎮貴格 */
    private fun yonchinkiKaku(): Int {
        var result = -1

        val shiNoArray = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val boolArray = booleanArrayOf(false, false, false, false)
        for (i in shiNoArray) {
            when (i) {
                2 -> boolArray[0] = true
                5 -> boolArray[1] = true
                8 -> boolArray[2] = true
                11 -> boolArray[3] = true
            }
        }

        if (boolArray.count { it } > 2)
            result = 1

        when (mYearShiNo) {
            2, 5, 8, 11 -> {
                if ((mMonthShiNo == (mYearShiNo.plus(2).rem(12).minus(2) + 3)) &&
                    (mDayShiNo == (mMonthShiNo.plus(2).rem(12).minus(2) + 3)))
                    result = 2
            }
        }

        return result
    }

    /** 36.四正格 */
    private fun yonseiKaku(): Int {

        var result = -1

        val shiNoArray = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val boolArray = booleanArrayOf(false, false, false, false)
        for (i in shiNoArray) {
            when (i) {
                1 -> boolArray[0] = true
                4 -> boolArray[1] = true
                7 -> boolArray[2] = true
                10 -> boolArray[3] = true
            }
        }

        if (boolArray.count { it } > 2)
            result = 1

        return result
    }

    /** 37.四生格 */
    private fun yonshouKaku(): Int {
        var result = -1

        val shiNoArray = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val boolArray = booleanArrayOf(false, false, false, false)
        for (i in shiNoArray) {
            when (i) {
                3 -> boolArray[0] = true
                6 -> boolArray[1] = true
                9 -> boolArray[2] = true
                12 -> boolArray[3] = true
            }
        }

        if (boolArray.count { it } > 2)
            result = 1

        return result
    }

    /** 38.子午双包格 */
    private fun shigosouhouKaku(): Int {
        var result = -1

        val shiNoArray: IntArray = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        val boolArray1 = booleanArrayOf(false, false)
        val boolArray2 = booleanArrayOf(false, false)
        val boolArray3 = booleanArrayOf(false, false)
        val boolArray4 = booleanArrayOf(false, false)
        val boolArray5 = booleanArrayOf(false, false)
        val boolArray6 = booleanArrayOf(false, false)
        val countArray = intArrayOf(0, 0, 0, 0, 0, 0)

        for (i in shiNoArray) {
            val param = i.minus(1).div(6)
            when (i) {
                1, 7 -> {
                    boolArray1[param] = true
                    countArray[0]++
                }
                2, 8 -> {
                    boolArray2[param] = true
                    countArray[1]++
                }
                3, 9 -> {
                    boolArray3[param] = true
                    countArray[2]++
                }
                4, 10 -> {
                    boolArray4[param] = true
                    countArray[3]++
                }
                5, 11 -> {
                    boolArray5[param] = true
                    countArray[4]++
                }
                6, 12 -> {
                    boolArray6[param] = true
                    countArray[5]++
                }
            }
        }

        if (((boolArray1.count { it } == 2) && countArray[0] == 3) ||
            ((boolArray2.count { it } == 2) && countArray[1] == 3) ||
            ((boolArray3.count { it } == 2) && countArray[2] == 3) ||
            ((boolArray4.count { it } == 2) && countArray[3] == 3) ||
            ((boolArray5.count { it } == 2) && countArray[4] == 3) ||
            ((boolArray6.count { it } == 2) && countArray[5] == 3))
            result = 1

        return result
    }

    /** 39.井蘭斜格 */
    private fun seiransyaKaku1(): Int {
        var result = -1

        if (!((mDayKanShiNo == 17) || (mDayKanShiNo == 37) || (mDayKanShiNo == 57)))
            return -1

        if ((mIsouUtil.getTaichuNo(mYearShiNo, mMonthShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mMonthShiNo, mDayShiNo) > 0) ||
            (mIsouUtil.getTaichuNo(mDayShiNo, mYearShiNo) > 0))
            return -1

        if ((mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo) == 5) ||
            (mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo) == 5) ||
            (mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo) == 5))
            result = 1

        return result
    }

    /** 40.井蘭叉格 */
    private fun seiransyaKaku2(): Int {
        var result = -1

        if (!((mDayKanShiNo == 17) || (mDayKanShiNo == 37) || (mDayKanShiNo == 57)))
            return -1

        if ((mIsouUtil.getTaichuNo(mYearShiNo, mMonthShiNo) == 0) &&
            (mIsouUtil.getTaichuNo(mMonthShiNo, mDayShiNo) == 0) &&
            (mIsouUtil.getTaichuNo(mDayShiNo, mYearShiNo) == 0))
            return -1

        if ((mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo) == 5) ||
            (mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo) == 5) ||
            (mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo) == 5))
            result = 1

        return result
    }

    /** 41.一気成生格 */
    private fun ikkiseisyouKaku(): Int {
        var result = -1

        val kanNoArray = intArrayOf(mYearKanNo, mMonthKanNo, mDayKanNo)
        val countArray = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in kanNoArray) {
            when (i) {
                in 1..9 -> countArray[i - 1]++
            }
        }
        val shiNoArray = intArrayOf(mYearShiNo, mMonthShiNo, mDayShiNo)
        for (i in shiNoArray) {
            when (i) {
                in 3..11 -> countArray[11 - i]++
            }
        }

        if ((mDayKanNo == 1) && (countArray[0] >= 5)) {
            result = 1
        } else if ((mDayKanNo == 2) && (countArray[1] >= 5)) {
            result = 2
        } else if ((mDayKanNo == 3) && (countArray[2] >= 5)) {
            result = 3
        } else if ((mDayKanNo == 4) && (countArray[3] >= 5)) {
            result = 4
        } else if ((mDayKanNo == 5) && (countArray[4] >= 5)) {
            result = 5
        } else if ((mDayKanNo == 6) && (countArray[5] >= 5)) {
            result = 6
        } else if ((mDayKanNo == 7) && (countArray[6] >= 5)) {
            result = 7
        } else if ((mDayKanNo == 8) && (countArray[7] >= 5)) {
            result = 8
        } else if ((mDayKanNo == 9) && (countArray[8] >= 5)) {
            result = 9
        }

        return result
    }

    /** 42.墓殺格 */
    private fun bosatsuKaku(): Int {
        var result = -1

        val yearNanasatsu = mUtil.isNanasatsu(mDayKanNo, mYearKanNo)
        val monthNanasatsu = mUtil.isNanasatsu(mDayKanNo, mMonthKanNo)
        val isKangou = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)

        if ((yearNanasatsu && (mYearShiType == 3)) &&
            (monthNanasatsu && (mMonthShiType == 3))) {
            result = 2
        } else if ((yearNanasatsu && (mYearShiType == 3) && (isKangou == 0)) ||
            (monthNanasatsu && (mMonthShiType == 3) && (isKangou == 0))) {
            result = 1
        }

        return result
    }

    /** 43.木性天干一気格 */
    private fun mokuseiTenkanikkiKaku(): Int {
        var result = -1

        if ((mYearKanType == 1) && (mMonthKanType == 1) && (mDayKanType == 1))
            result = 1

        if (((mYearKanNo == 1) && (mMonthKanNo == 1) && (mDayKanNo == 1)) ||
            ((mYearKanNo == 2) && (mMonthKanNo == 2) && (mDayKanNo == 2)))
            result = 2

        return result
    }

    /** 44.火性天干一気格 */
    private fun kaseiTenkanikkiKaku(): Int {
        var result = -1

        if ((mYearKanType == 2) && (mMonthKanType == 2) && (mDayKanType == 2))
            result = 1

        if (((mYearKanNo == 3) && (mMonthKanNo == 3) && (mDayKanNo == 3)) ||
            ((mYearKanNo == 4) && (mMonthKanNo == 4) && (mDayKanNo == 4)))
            result = 2

        return result
    }

    /** 45.土性天干一気格 */
    private fun doseiTenkanikkiKaku(): Int {
        var result = -1

        if ((mYearKanType == 3) && (mMonthKanType == 3) && (mDayKanType == 3))
            result = 1

        if (((mYearKanNo == 5) && (mMonthKanNo == 5) && (mDayKanNo == 5)) ||
            ((mYearKanNo == 6) && (mMonthKanNo == 6) && (mDayKanNo == 6)))
            result = 2

        return result
    }

    /** 46.金性天干一気格 */
    private fun kinseiTenkanikkiKaku(): Int {
        var result = -1

        if ((mYearKanType == 4) && (mMonthKanType == 4) && (mDayKanType == 4))
            result = 1

        if (((mYearKanNo == 7) && (mMonthKanNo == 7) && (mDayKanNo == 7)) ||
            ((mYearKanNo == 8) && (mMonthKanNo == 8) && (mDayKanNo == 8)))
            result = 2

        return result
    }

    /** 47.水性天干一気格 */
    private fun suiseiTenkanikkiKaku(): Int {
        var result = -1

        if ((mYearKanType == 5) && (mMonthKanType == 5) && (mDayKanType == 5))
            result = 1

        if (((mYearKanNo == 9) && (mMonthKanNo == 9) && (mDayKanNo == 9)) ||
            ((mYearKanNo == 10) && (mMonthKanNo == 10) && (mDayKanNo == 10)))
            result = 2

        return result
    }

    /** 48.木性地支一気格 */
    private fun mokuseiChishiikkiKaku(): Int {
        var result = -1

        if ((mYearShiType == 1) && (mMonthShiType == 1) && (mDayShiType == 1))
            result = 1

        return result
    }

    /** 49.火性地支一気格 */
    private fun kaseiChishiikkiKaku(): Int {
        var result = -1

        if ((mYearShiType == 2) && (mMonthShiType == 2) && (mDayShiType == 2))
            result = 1

        return result
    }

    /** 50.土性地支一気格 */
    private fun doseiChishiikkiKaku(): Int {
        var result = -1

        if ((mYearShiType == 3) && (mMonthShiType == 3) && (mDayShiType == 3))
            result = 1

        return result
    }

    /** 51.金性地支一気格 */
    private fun kinseiChishiikkiKaku(): Int {
        var result = -1

        if ((mYearShiType == 4) && (mMonthShiType == 4) && (mDayShiType == 4))
            result = 1

        return result
    }

    /** 52.水性地支一気格 */
    private fun suiseiChishiikkiKaku(): Int {
        var result = -1

        if ((mYearShiType == 5) && (mMonthShiType == 5) && (mDayShiType == 5))
            result = 1

        return result
    }

    /** 53.木性一気格 */
    private fun mokuseiIkkiKaku(): Int {
        var result = -1

        if (mDayKanType != 1)
            return -1

        val gogyouArray = intArrayOf(mYearKanType, mMonthKanType, mDayKanType, mYearShiType, mMonthShiType, mDayShiType)
        if (gogyouArray.count { it == 1 } == 6) {
            result = 1
            return result
        }

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if ((kangouNo == 1) || (kangouNo == 2)) {
            gogyouArray[0] = 1
            gogyouArray[1] = 1
        }

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if ((shigouNo1 == 1) || (shigouNo1 == 6)) {
                gogyouArray[3] = 1
                gogyouArray[4] = 1
            }
            if ((shigouNo2 == 1) || (shigouNo2 == 6)) {
                gogyouArray[4] = 1
                gogyouArray[5] = 1
            }
            if ((shigouNo3 == 1) || (shigouNo3 == 6)) {
                gogyouArray[5] = 1
                gogyouArray[3] = 1
            }
        } else {
            if (hankaiNo1 == 1) {
                gogyouArray[3] = 1
                gogyouArray[4] = 1
                if ((shigouNo2 == 1) || (shigouNo3 == 1) || (shigouNo2 == 6) || (shigouNo3 == 6))
                    gogyouArray[5] = 1
            }
            if (hankaiNo2 == 1) {
                gogyouArray[4] = 1
                gogyouArray[5] = 1
                if ((shigouNo3 == 1) || (shigouNo1 == 1) || (shigouNo3 == 6) || (shigouNo1 == 6))
                    gogyouArray[3] = 1
            }
            if (hankaiNo3 == 1) {
                gogyouArray[5] = 1
                gogyouArray[3] = 1
                if ((shigouNo1 == 1) || (shigouNo2 == 1) || (shigouNo1 == 6) || (shigouNo2 == 6))
                    gogyouArray[4] = 1
            }
        }

        if (gogyouArray.count { it == 1 } == 6)
            result = 0

        return result
    }

    /** 54.火性一気格 */
    private fun kaseiIkkiKaku(): Int {
        var result = -1

        if (mDayKanType != 2)
            return -1

        val gogyouArray = intArrayOf(mYearKanType, mMonthKanType, mDayKanType, mYearShiType, mMonthShiType, mDayShiType)
        if (gogyouArray.count { it == 2 } == 6) {
            result = 1
            return result
        }

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if ((kangouNo == 3) || (kangouNo == 4)) {
            gogyouArray[0] = 2
            gogyouArray[1] = 2
        }

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if (shigouNo1 == 2) {
                gogyouArray[3] = 2
                gogyouArray[4] = 2
            }
            if (shigouNo2 == 2) {
                gogyouArray[4] = 2
                gogyouArray[5] = 2
            }
            if (shigouNo3 == 2) {
                gogyouArray[5] = 2
                gogyouArray[3] = 2
            }
        } else {
            if (hankaiNo1 == 2) {
                gogyouArray[3] = 2
                gogyouArray[4] = 2
                if ((shigouNo2 == 2) || (shigouNo3 == 2))
                    gogyouArray[5] = 2
            }
            if (hankaiNo2 == 2) {
                gogyouArray[4] = 2
                gogyouArray[5] = 2
                if ((shigouNo3 == 2) || (shigouNo1 == 2))
                    gogyouArray[3] = 2
            }
            if (hankaiNo3 == 2) {
                gogyouArray[5] = 2
                gogyouArray[3] = 2
                if ((shigouNo1 == 2) || (shigouNo2 == 2))
                    gogyouArray[4] = 2
            }
        }

        if (gogyouArray.count { it == 2 } == 6)
            result = 0

        return result
    }

    /** 55.土性一気格 */
    private fun doseiIkkiKaku(): Int {
        var result = -1

        if (mDayKanType != 3)
            return -1

        val gogyouArray = intArrayOf(mYearKanType, mMonthKanType, mDayKanType, mYearShiType, mMonthShiType, mDayShiType)
        if (gogyouArray.count { it == 3 } == 6) {
            result = 1
            return result
        }

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if ((kangouNo == 5) || (kangouNo == 6)) {
            gogyouArray[0] = 3
            gogyouArray[1] = 3
        }

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if ((shigouNo1 == 6) || (shigouNo1 == 7)) {
                gogyouArray[3] = 3
                gogyouArray[4] = 3
            }
            if ((shigouNo2 == 6) || (shigouNo2 == 7)) {
                gogyouArray[4] = 3
                gogyouArray[5] = 3
            }
            if ((shigouNo3 == 6) || (shigouNo3 == 7)) {
                gogyouArray[5] = 3
                gogyouArray[3] = 3
            }
        }

        if (gogyouArray.count { it == 3 } == 6)
            result = 0

        return result
    }

    /** 56.金性一気格 */
    private fun kinseiIkkiKaku(): Int {
        var result = -1

        if (mDayKanType != 4)
            return -1

        val gogyouArray = intArrayOf(mYearKanType, mMonthKanType, mDayKanType, mYearShiType, mMonthShiType, mDayShiType)
        if (gogyouArray.count { it == 4 } == 6) {
            result = 1
            return result
        }

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if ((kangouNo == 7) || (kangouNo == 8)) {
            gogyouArray[0] = 4
            gogyouArray[1] = 4
        }

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if ((shigouNo1 == 4) || (shigouNo1 == 7)) {
                gogyouArray[3] = 4
                gogyouArray[4] = 4
            }
            if ((shigouNo2 == 4) || (shigouNo2 == 7)) {
                gogyouArray[4] = 4
                gogyouArray[5] = 4
            }
            if ((shigouNo3 == 4) || (shigouNo3 == 7)) {
                gogyouArray[5] = 4
                gogyouArray[3] = 4
            }
        } else {
            if (hankaiNo1 == 4) {
                gogyouArray[3] = 4
                gogyouArray[4] = 4
                if ((shigouNo2 == 4) || (shigouNo3 == 4) || (shigouNo2 == 7) || (shigouNo3 == 7))
                    gogyouArray[5] = 4
            }
            if (hankaiNo2 == 4) {
                gogyouArray[4] = 4
                gogyouArray[5] = 4
                if ((shigouNo3 == 4) || (shigouNo1 == 4) || (shigouNo3 == 7) || (shigouNo1 == 7))
                    gogyouArray[3] = 4
            }
            if (hankaiNo3 == 4) {
                gogyouArray[5] = 4
                gogyouArray[3] = 4
                if ((shigouNo1 == 4) || (shigouNo2 == 4) || (shigouNo1 == 7) || (shigouNo2 == 7))
                    gogyouArray[4] = 4
            }
        }

        if (gogyouArray.count { it == 4 } == 6)
            result = 0

        return result
    }

    /** 57.水性一気格 */
    private fun suiseiIkkiKaku(): Int {
        var result = -1

        if (mDayKanType != 5)
            return -1

        val gogyouArray = intArrayOf(mYearKanType, mMonthKanType, mDayKanType, mYearShiType, mMonthShiType, mDayShiType)
        if (gogyouArray.count { it == 5 } == 6) {
            result = 1
            return result
        }

        val kangouNo = mUtil.getKangouNo(mYearKanNo, mMonthKanNo)
        if ((kangouNo == 9) || (kangouNo == 10)) {
            gogyouArray[0] = 5
            gogyouArray[1] = 5
        }

        val hankaiNo1 = mIsouUtil.getHankaiNo(mYearShiNo, mMonthShiNo)
        val hankaiNo2 = mIsouUtil.getHankaiNo(mMonthShiNo, mDayShiNo)
        val hankaiNo3 = mIsouUtil.getHankaiNo(mDayShiNo, mYearShiNo)
        val shigouNo1 = mIsouUtil.getShigouNo(mYearShiNo, mMonthShiNo)
        val shigouNo2 = mIsouUtil.getShigouNo(mMonthShiNo, mDayShiNo)
        val shigouNo3 = mIsouUtil.getShigouNo(mDayShiNo, mYearShiNo)
        if ((hankaiNo1 == 0) && (hankaiNo2 == 0) && (hankaiNo3 == 0)) {
            if (shigouNo1 == 5) {
                gogyouArray[3] = 5
                gogyouArray[4] = 5
            }
            if (shigouNo2 == 5) {
                gogyouArray[4] = 5
                gogyouArray[5] = 5
            }
            if (shigouNo3 == 5) {
                gogyouArray[5] = 5
                gogyouArray[3] = 5
            }
        } else {
            if (hankaiNo1 == 5) {
                gogyouArray[3] = 5
                gogyouArray[4] = 5
                if ((shigouNo2 == 5) || (shigouNo3 == 5))
                    gogyouArray[5] = 5
            }
            if (hankaiNo2 == 5) {
                gogyouArray[4] = 5
                gogyouArray[5] = 5
                if ((shigouNo3 == 5) || (shigouNo1 == 5))
                    gogyouArray[3] = 5
            }
            if (hankaiNo3 == 5) {
                gogyouArray[5] = 5
                gogyouArray[3] = 5
                if ((shigouNo1 == 5) || (shigouNo2 == 5))
                    gogyouArray[4] = 5
            }
        }

        if (gogyouArray.count { it == 5 } == 6)
            result = 0

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