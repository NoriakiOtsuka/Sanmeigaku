package com.example.sanmeigaku.Util

import android.util.Log
import com.example.sanmeigaku.AssessmentActivity
import com.example.sanmeigaku.Enum.Kyoku

class Kyokuhou {
    private val TAG: String = "Kyokuhou"
    private val activity: AssessmentActivity.Companion = AssessmentActivity

    /** Variables of main star number received from the assessment activity */
    private val mMainStar: Int = activity.mMainStarNo
    private val mMainStar1: Int = activity.mMainStar1No
    private val mMainStar2: Int = activity.mMainStar2No
    private val mMainStar3: Int = activity.mMainStar3No
    private val mMainStar4: Int = activity.mMainStar4No

    /** Variables of main star gogyou type */
    private var mMainType: Int = 0
    private var mMainType1: Int = 0
    private var mMainType2: Int = 0
    private var mMainType3: Int = 0
    private var mMainType4: Int = 0

    /**
     * Set kyokuhou items
     */
    fun setKyokuhou(): String {
        mMainType = setGogyouFromMainStar(mMainStar)
        mMainType1 = setGogyouFromMainStar(mMainStar1)
        mMainType2 = setGogyouFromMainStar(mMainStar2)
        mMainType3 = setGogyouFromMainStar(mMainStar3)
        mMainType4 = setGogyouFromMainStar(mMainStar4)

        Log.i(TAG, "setKyokuhou: Calculate and set the appropriate Kyoku")

        val array = booleanArrayOf(
            suigyakuKyoku(),
            ensuiKyoku(),
            syousatsuKyoku(),
            satsuKyoku(),
            seiranKyoku(),
            ranmeiKyoku(),
            hazaiKyoku(),
            kyokuzaiKyoku(),
            chizaiKyoku(),
            syazaiKyoku(),
            houranKyoku(),
            junhouranKyoku(),
            houjunKyoku(),
            hachijuKyoku(),
            sanreiKyoku(),
            souhouKyoku(),
            genryuuKyoku(),
            kyakuhouKyoku(),
            tenkaKyoku(),
            renzaiKyoku(),
            houkanKyoku(),
            meiryuuKyoku(),
            kyakuKyoku(),
            syokuKyoku(),
            zaiKyoku(),
            kanKyoku(),
            inKyoku()
        )
        var result = ""
        for ((index, i) in array.withIndex()) {
            if (i)
                result += Kyoku.valueOf("Kyoku${index + 1}").value + ", "
        }

        return result.dropLast(2)
    }

    /** 1.推逆局 */
    private fun suigyakuKyoku(): Boolean {
        val boolArray = booleanArrayOf(false, false)
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)

        if ((mMainStar != 3) && (mMainStar != 4) && (mMainStar != 9) && (mMainStar != 10))
            return false

        for (type in verticalArray) {
            when (type) {
                2 -> boolArray[1] = true
                5 -> boolArray[0] = true
            }
        }
        if (boolArray.count {it} != 2)
            return false

        return true
    }

    /** 2.円推局 */
    private fun ensuiKyoku(): Boolean {
        val boolArray = booleanArrayOf(false, false)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)

        if ((mMainStar != 3) && (mMainStar != 4) && (mMainStar != 9) && (mMainStar != 10))
            return false

        for (type in horizontalArray) {
            when (type) {
                2 -> boolArray[1] = true
                5 -> boolArray[0] = true
            }
        }
        if (boolArray.count {it} != 2)
            return false

        return true
    }

    /** 3.生殺局 */
    private fun syousatsuKyoku(): Boolean {
        val boolArray = booleanArrayOf(false, false)
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)

        if ((mMainStar != 3) && (mMainStar != 4) && (mMainStar != 7) && (mMainStar != 8))
            return false

        for (type in verticalArray) {
            when (type) {
                2 -> boolArray[0] = true
                4 -> boolArray[1] = true
            }
        }
        if (boolArray.count {it} != 2)
            return false

        return true
    }

    /** 4.殺局 */
    private fun satsuKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)

        if ((mMainStar != 3) && (mMainStar != 4) && (mMainStar != 7) && (mMainStar != 8))
            return false

        for (type in horizontalArray) {
            when (type) {
                2 -> resultArray[0] = true
                4 -> resultArray[1] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 5.井乱局 */
    private fun seiranKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)

        if ((mMainStar != 1) && (mMainStar != 2) && (mMainStar != 7) && (mMainStar != 8))
            return false

        for (type in verticalArray) {
            when (type) {
                1 -> resultArray[1] = true
                4 -> resultArray[0] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 6.乱命局 */
    private fun ranmeiKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)

        if ((mMainStar != 1) && (mMainStar != 2) && (mMainStar != 7) && (mMainStar != 8))
            return false

        for (type in horizontalArray) {
            when (type) {
                1 -> resultArray[1] = true
                4 -> resultArray[0] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 7.破財局 */
    private fun hazaiKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)

        if ((mMainStar != 1) && (mMainStar != 2) && (mMainStar != 5) && (mMainStar != 6))
            return false

        for (type in verticalArray) {
            when (type) {
                1 -> resultArray[0] = true
                3 -> resultArray[1] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 8.曲財局 */
    private fun kyokuzaiKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)

        if ((mMainStar != 1) && (mMainStar != 2) && (mMainStar != 5) && (mMainStar != 6))
            return false

        for (type in horizontalArray) {
            when (type) {
                1 -> resultArray[0] = true
                3 -> resultArray[1] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 9.地財局 */
    private fun chizaiKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)

        if ((mMainStar != 5) && (mMainStar != 6) && (mMainStar != 9) && (mMainStar != 10))
            return false

        for (type in verticalArray) {
            when (type) {
                3 -> resultArray[0] = true
                5 -> resultArray[1] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 10.叉財局 */
    private fun syazaiKyoku(): Boolean {
        val resultArray = booleanArrayOf(false, false)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)

        if ((mMainStar != 5) && (mMainStar != 6) && (mMainStar != 9) && (mMainStar != 10))
            return false

        for (type in horizontalArray) {
            when (type) {
                3 -> resultArray[0] = true
                5 -> resultArray[1] = true
            }
        }
        if (resultArray.count {it} != 2)
            return false

        return true
    }

    /** 11.鳳蘭局 */
    private fun houranKyoku(): Boolean {
        val mainStarArray = intArrayOf(mMainStar1, mMainStar2, mMainStar3, mMainStar4)

        for (star in mainStarArray) {
            if (star != mMainStar)
                return false
        }

        return true
    }

    /** 12.準鳳蘭局 */
    private fun junhouranKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /** 13.芳順局 */
    private fun houjunKyoku(): Boolean {
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[0]) {
            1 -> when (verticalArray[1]) {
                1 -> when (verticalArray[2]) {
                    1, 5 -> return true
                }
                4 -> when (verticalArray[2]) {
                    5 -> return true
                }
                5 -> when (verticalArray[2]) {
                    5 -> return true
                }
            }
        }

        when (horizontalArray[0]) {
            1 -> when (horizontalArray[1]) {
                1 -> when (horizontalArray[2]) {
                    1, 5 -> return true
                }
                4 -> when (horizontalArray[2]) {
                    5 -> return true
                }
                5 -> when (horizontalArray[2]) {
                    5 -> return true
                }
            }
        }

        return false
    }

    /** 14.八寿局 */
    private fun hachijuKyoku(): Boolean {
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[0]) {
            1 -> when (verticalArray[1]) {
                1 -> when (verticalArray[2]) {
                    2 -> return true
                }
                2 -> when (verticalArray[2]) {
                    2, 5 -> return true
                }
            }
            2 -> when (verticalArray[1]) {
                2 -> when (verticalArray[2]) {
                    2 -> return true
                }
            }
        }

        when (horizontalArray[0]) {
            1 -> when (horizontalArray[1]) {
                1 -> when (horizontalArray[2]) {
                    2 -> return true
                }
                2 -> when (horizontalArray[2]) {
                    2, 5 -> return true
                }
            }
            2 -> when (horizontalArray[1]) {
                2 -> when (horizontalArray[2]) {
                    2 -> return true
                }
            }
        }

        return false
    }

    /** 15.三麗局 */
    private fun sanreiKyoku(): Boolean {
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            3 -> when (verticalArray[1]) {
                2 -> when (verticalArray[0]) {
                    1, 2 -> return true
                }
                3 -> when (verticalArray[0]) {
                    2, 3 -> return true
                }
            }
        }

        when (horizontalArray[2]) {
            3 -> when (horizontalArray[1]) {
                2 -> when (horizontalArray[0]) {
                    1, 2 -> return true
                }
                3 -> when (horizontalArray[0]) {
                    2, 3 -> return true
                }
            }
        }

        return false
    }

    /** 16.相法局 */
    private fun souhouKyoku(): Boolean {
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            4 -> when (verticalArray[1]) {
                3 -> when (verticalArray[0]) {
                    2, 3 -> return true
                }
                4 -> when (verticalArray[0]) {
                    3, 4 -> return true
                }
            }
        }

        when (horizontalArray[2]) {
            4 -> when (horizontalArray[1]) {
                3 -> when (horizontalArray[0]) {
                    2, 3 -> return true
                }
                4 -> when (horizontalArray[0]) {
                    3, 4 -> return true
                }
            }
        }

        return false
    }

    /** 17.玄流局 */
    private fun genryuuKyoku(): Boolean {
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            5 -> when (verticalArray[1]) {
                4 -> when (verticalArray[0]) {
                    3, 4 -> return true
                }
                5 -> when (verticalArray[0]) {
                    4, 5 -> return true
                }
            }
        }

        when (horizontalArray[2]) {
            4 -> when (horizontalArray[1]) {
                3 -> when (horizontalArray[0]) {
                    2, 3 -> return true
                }
                4 -> when (horizontalArray[0]) {
                    3, 4 -> return true
                }
            }
        }

        return false
    }

    /** 18.却法局 */
    private fun kyakuhouKyoku(): Boolean {
        var isVertical = false
        var ishorizontal = false
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[0]) {
            1 -> when (verticalArray[1]) {
                1 -> when (verticalArray[2]) {
                    1, 5 -> isVertical = true
                }
                4 -> when (verticalArray[2]) {
                    5 -> isVertical = true
                }
                5 -> when (verticalArray[2]) {
                    5 -> isVertical = true
                }
            }
        }

        when (horizontalArray[0]) {
            1 -> when (horizontalArray[1]) {
                1 -> when (horizontalArray[2]) {
                    1, 5 -> ishorizontal = true
                }
                4 -> when (horizontalArray[2]) {
                    5 -> ishorizontal = true
                }
                5 -> when (horizontalArray[2]) {
                    5 -> ishorizontal = true
                }
            }
        }

        if (isVertical && ishorizontal)
            return true

        return false
    }

    /** 19.天華局 */
    private fun tenkaKyoku(): Boolean {
        var isVertical = false
        var ishorizontal = false
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[0]) {
            1 -> when (verticalArray[1]) {
                1 -> when (verticalArray[2]) {
                    2 -> isVertical = true
                }
                2 -> when (verticalArray[2]) {
                    2, 5 -> isVertical = true
                }
            }
            2 -> when (verticalArray[1]) {
                2 -> when (verticalArray[2]) {
                    2 -> isVertical = true
                }
            }
        }

        when (horizontalArray[0]) {
            1 -> when (horizontalArray[1]) {
                1 -> when (horizontalArray[2]) {
                    2 -> ishorizontal = true
                }
                2 -> when (horizontalArray[2]) {
                    2, 5 -> ishorizontal = true
                }
            }
            2 -> when (horizontalArray[1]) {
                2 -> when (horizontalArray[2]) {
                    2 -> ishorizontal = true
                }
            }
        }

        if (isVertical && ishorizontal)
            return true

        return false
    }

    /** 20.連財局 */
    private fun renzaiKyoku(): Boolean {
        var isVertical = false
        var ishorizontal = false
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            3 -> when (verticalArray[1]) {
                2 -> when (verticalArray[0]) {
                    1, 2 -> isVertical = true
                }
                3 -> when (verticalArray[0]) {
                    2, 3 -> isVertical = true
                }
            }
        }

        when (horizontalArray[2]) {
            3 -> when (horizontalArray[1]) {
                2 -> when (horizontalArray[0]) {
                    1, 2 -> ishorizontal = true
                }
                3 -> when (horizontalArray[0]) {
                    2, 3 -> ishorizontal = true
                }
            }
        }

        if (isVertical && ishorizontal)
            return true

        return false
    }

    /** 21.法官局 */
    private fun houkanKyoku(): Boolean {
        var isVertical = false
        var ishorizontal = false
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            4 -> when (verticalArray[1]) {
                3 -> when (verticalArray[0]) {
                    2, 3 -> isVertical = true
                }
                4 -> when (verticalArray[0]) {
                    3, 4 -> isVertical = true
                }
            }
        }

        when (horizontalArray[2]) {
            4 -> when (horizontalArray[1]) {
                3 -> when (horizontalArray[0]) {
                    2, 3 -> ishorizontal = true
                }
                4 -> when (horizontalArray[0]) {
                    3, 4 -> ishorizontal = true
                }
            }
        }

        if (isVertical && ishorizontal)
            return true

        return false
    }

    /** 22.命龍局 */
    private fun meiryuuKyoku(): Boolean {
        var isVertical = false
        var ishorizontal = false
        val verticalArray = arrayListOf(mMainType, mMainType2, mMainType4)
        val horizontalArray = arrayListOf(mMainType, mMainType1, mMainType3)
        verticalArray.sort()
        horizontalArray.sort()

        when (verticalArray[2]) {
            5 -> when (verticalArray[1]) {
                4 -> when (verticalArray[0]) {
                    3, 4 -> isVertical = true
                }
                5 -> when (verticalArray[0]) {
                    4, 5 -> isVertical = true
                }
            }
        }

        when (horizontalArray[2]) {
            5 -> when (horizontalArray[1]) {
                4 -> when (horizontalArray[0]) {
                    3, 4 -> ishorizontal = true
                }
                5 -> when (horizontalArray[0]) {
                    4, 5 -> ishorizontal = true
                }
            }
        }

        if (isVertical && ishorizontal)
            return true

        return false
    }

    /** 23.却局 */
    private fun kyakuKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        if (mMainType != 1)
            return false

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /** 24.食局 */
    private fun syokuKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        if (mMainType != 2)
            return false

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /** 25.財局 */
    private fun zaiKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        if (mMainType != 3)
            return false

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /** 26.官局 */
    private fun kanKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        if (mMainType != 4)
            return false

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /** 27.印局 */
    private fun inKyoku(): Boolean {
        val mainTypeArray = intArrayOf(mMainType1, mMainType2, mMainType3, mMainType4)

        if (mMainType != 5)
            return false

        for (type in mainTypeArray) {
            if (type != mMainType)
                return false
        }

        return true
    }

    /**
     * Set gogyou type of main star
     */
    private fun setGogyouFromMainStar(num: Int): Int {
        var type = 0
        when (num) {
            1, 2 -> type = 1
            3, 4 -> type = 2
            5, 6 -> type = 3
            7, 8 -> type = 4
            9, 10 -> type = 5
            else -> {
                Log.e(TAG, "setGogyouFromMainStar: The variable \"type\" must not be $type.")
            }
        }

        return type
    }
}