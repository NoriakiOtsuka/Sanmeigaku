package com.example.sanmeigaku.Util

import android.content.Context
import com.example.sanmeigaku.Enum.KanShi
import com.example.sanmeigaku.R

class Isouhou {
    /**
     * Get Gouhou and Sanhou number
     */
    fun getKouTenUnIsouNo(shiNo: Int, taiShiNo: Int): Int {
        val num = when (shiNo) {
            1 -> {
                when (taiShiNo) {
                    2 -> 25
                    4 -> 52
                    5 -> 18
                    7 -> 41
                    8 -> 71
                    9 -> 18
                    10 -> 61
                    else -> 0
                }
            }
            2 -> {
                when (taiShiNo) {
                    1 -> 25
                    5 -> 61
                    6 -> 17
                    7 -> 71
                    8 -> 5443
                    10 -> 17
                    11 -> 54
                    else -> 0
                }
            }
            3 -> {
                when (taiShiNo) {
                    6 -> 5371
                    7 -> 16
                    9 -> 5342
                    11 -> 16
                    12 -> 2161
                    else -> 0
                }
            }
            4 -> {
                when (taiShiNo) {
                    1 -> 52
                    5 -> 71
                    7 -> 61
                    8 -> 15
                    10 -> 41
                    11 -> 2123
                    12 -> 15
                    else -> 0
                }
            }
            5 -> {
                when (taiShiNo) {
                    1 -> 18
                    2 -> 61
                    4 -> 71
                    5 -> 51
                    9 -> 18
                    10 -> 2423
                    11 -> 43
                    else -> 0
                }
            }
            6 -> {
                when (taiShiNo) {
                    2 -> 17
                    3 -> 5371
                    9 -> 245361
                    10 -> 17
                    12 -> 42
                    else -> 0
                }
            }
            7 -> {
                when (taiShiNo) {
                    1 -> 41
                    2 -> 71
                    3 -> 16
                    4 -> 61
                    7 -> 51
                    8 -> 22
                    11 -> 16
                    else -> 0
                }
            }
            8 -> {
                when (taiShiNo) {
                    1 -> 71
                    2 -> 5443
                    4 -> 15
                    7 -> 22
                    11 -> 5461
                    12 -> 15
                    else -> 0
                }
            }
            9 -> {
                when (taiShiNo) {
                    1 -> 18
                    3 -> 5342
                    5 -> 18
                    6 -> 245361
                    12 -> 71
                    else -> 0
                }
            }
            10 -> {
                when (taiShiNo) {
                    1 -> 61
                    2 -> 17
                    4 -> 41
                    5 -> 2423
                    6 -> 17
                    10 -> 51
                    11 -> 71
                    else -> 0
                }
            }
            11 -> {
                when (taiShiNo) {
                    2 -> 54
                    3 -> 16
                    4 -> 2123
                    5 -> 43
                    7 -> 16
                    8 -> 5461
                    10 -> 71
                    else -> 0
                }
            }
            12 -> {
                when (taiShiNo) {
                    3 -> 2161
                    4 -> 15
                    6 -> 42
                    8 -> 15
                    9 -> 71
                    12 -> 51
                    else -> 0
                }
            }
            else -> 0
        }

        return num
    }

    /**
     * Get SangouKaikyoku assessment result
     */
    fun getSangouKaikyoku(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val mokuArray = booleanArrayOf(false, false, false)
        val kaArray = booleanArrayOf(false, false, false)
        val gonArray = booleanArrayOf(false, false, false)
        val suiArray = booleanArrayOf(false, false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> suiArray[0] = true
                2 -> gonArray[0] = true
                3 -> kaArray[0] = true
                4 -> mokuArray[0] = true
                5 -> suiArray[1] = true
                6 -> gonArray[1] = true
                7 -> kaArray[1] = true
                8 -> mokuArray[1] = true
                9 -> suiArray[2] = true
                10 -> gonArray[2] = true
                11 -> kaArray[2] = true
                12 -> mokuArray[2] = true
            }
        }

        val yearShi = KanShi.valueOf("Shi$yearShiNo").value
        val monthShi = KanShi.valueOf("Shi$monthShiNo").value
        val dayShi = KanShi.valueOf("Shi$dayShiNo").value
        val mokuSangou = context.getString(R.string.isouhou_sangoukaikyoku_moku_text)
        val kaSangou = context.getString(R.string.isouhou_sangoukaikyoku_ka_text)
        val gonSangou = context.getString(R.string.isouhou_sangoukaikyoku_gon_text)
        val suiSangou = context.getString(R.string.isouhou_sangoukaikyoku_sui_text)
        var result = ""

        if (mokuArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $mokuSangou"
        } else if (kaArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $kaSangou"
        } else if (gonArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $gonSangou"
        } else if (suiArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $suiSangou"
        }

        return result
    }

    /**
     * Get Hankai assessment result
     */
    fun getHankai(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val yearShi = KanShi.valueOf("Shi$yearShiNo").value
        val monthShi = KanShi.valueOf("Shi$monthShiNo").value
        val dayShi = KanShi.valueOf("Shi$dayShiNo").value
        val mokuHankai = context.getString(R.string.isouhou_hankai_moku_text)
        val kaHankai = context.getString(R.string.isouhou_hankai_ka_text)
        val gonHankai = context.getString(R.string.isouhou_hankai_gon_text)
        val suiHankai = context.getString(R.string.isouhou_hankai_sui_text)
        var result = ""

        if ((yearShiNo % 4) == (monthShiNo % 4) && (yearShiNo != monthShiNo)) {
            when (yearShiNo % 4) {
                0 -> result = "＿-$monthShi-$yearShi  $mokuHankai"
                1 -> result = "＿-$monthShi-$yearShi  $suiHankai"
                2 -> result = "＿-$monthShi-$yearShi  $gonHankai"
                3 -> result = "＿-$monthShi-$yearShi  $kaHankai"
            }
        } else if ((monthShiNo % 4) == (dayShiNo % 4) && (monthShiNo != dayShiNo)) {
            when (monthShiNo % 4) {
                0 -> result = "$dayShi-$monthShi-＿  $mokuHankai"
                1 -> result = "$dayShi-$monthShi-＿  $suiHankai"
                2 -> result = "$dayShi-$monthShi-＿  $gonHankai"
                3 -> result = "$dayShi-$monthShi-＿  $kaHankai"
            }
        } else if ((yearShiNo % 4) == (dayShiNo % 4) && (yearShiNo != dayShiNo)) {
            when (yearShiNo % 4) {
                0 -> result = "$dayShi-＿-$yearShi  $mokuHankai"
                1 -> result = "$dayShi-＿-$yearShi  $suiHankai"
                2 -> result = "$dayShi-＿-$yearShi  $gonHankai"
                3 -> result = "$dayShi-＿-$yearShi  $kaHankai"
            }
        }

        return result
    }

    /**
     * Get Shigou assessment result
     */
    fun getShigou(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val hoppouArray = IntArray(3)
        val touhouArray = IntArray(3)
        val chuouArray = IntArray(3)
        val tenjikuArray = IntArray(3)
        val seihouArray = IntArray(3)
        val nanpouArray = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 2 -> hoppouArray[index] = i
                3, 12 -> touhouArray[index] = i
                4, 11 -> chuouArray[index] = i
                5, 10 -> tenjikuArray[index] = i
                6, 9 -> seihouArray[index] = i
                7, 8 -> nanpouArray[index] = i
            }
        }

        val hoppouShigou = context.getString(R.string.isouhou_shigou_hoku_text)
        val touhouShigou = context.getString(R.string.isouhou_shigou_tou_text)
        val chuouShigou = context.getString(R.string.isouhou_shigou_chu_text)
        val tenjikuShigou = context.getString(R.string.isouhou_shigou_ten_text)
        val seihouShigou = context.getString(R.string.isouhou_shigou_sei_text)
        val nanpouShigou = context.getString(R.string.isouhou_shigou_nan_text)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"
        var result = ""

        if (hoppouArray.count { it == 0 } < 2) {
            val hoppouMaxNo = hoppouArray.maxBy { it }
            val hoppouMinNo = hoppouArray.filter { it > 0 }.minBy { it }
            if (hoppouMaxNo != hoppouMinNo) {
                if (hoppouArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (hoppouArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (hoppouArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $hoppouShigou"
            }
        } else if (touhouArray.count { it == 0 } < 2) {
            val touhouMaxNo = touhouArray.maxBy { it }
            val touhouMinNo = touhouArray.filter { it > 0 }.minBy { it }
            if (touhouMaxNo != touhouMinNo) {
                if (touhouArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (touhouArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (touhouArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $touhouShigou"
            }
        } else if (chuouArray.count { it == 0 } < 2) {
            val chuouMaxNo = chuouArray.maxBy { it }
            val chuouMinNo = chuouArray.filter { it > 0 }.minBy { it }
            if (chuouMaxNo != chuouMinNo) {
                if (chuouArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (chuouArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (chuouArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $chuouShigou"
            }
        } else if (tenjikuArray.count { it == 0 } < 2) {
            val tenjikuMaxNo = tenjikuArray.maxBy { it }
            val tenjikuMinNo = tenjikuArray.filter { it > 0 }.minBy { it }
            if (tenjikuMaxNo != tenjikuMinNo) {
                if (tenjikuArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (tenjikuArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (tenjikuArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $tenjikuShigou"
            }
        } else if (seihouArray.count { it == 0 } < 2) {
            val seihouMaxNo = seihouArray.maxBy { it }
            val seihouMinNo = seihouArray.filter { it > 0 }.minBy { it }
            if (seihouMaxNo != seihouMinNo) {
                if (seihouArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (seihouArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (seihouArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $seihouShigou"
            }
        } else if (nanpouArray.count { it == 0 } < 2) {
            val nanpouMaxNo = nanpouArray.maxBy { it }
            val nanpouMinNo = nanpouArray.filter { it > 0 }.minBy { it }
            if (nanpouMaxNo != nanpouMinNo) {
                if (nanpouArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (nanpouArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (nanpouArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $nanpouShigou"
            }
        }

        return result
    }

    /**
     * Get Housani assessment result
     */
    fun getHousani(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val touhouArray = booleanArrayOf(false, false, false)
        val nanpouArray = booleanArrayOf(false, false, false)
        val seihouArray = booleanArrayOf(false, false, false)
        val hoppouArray = booleanArrayOf(false, false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> hoppouArray[1] = true
                2 -> hoppouArray[2] = true
                3 -> touhouArray[0] = true
                4 -> touhouArray[1] = true
                5 -> touhouArray[2] = true
                6 -> nanpouArray[0] = true
                7 -> nanpouArray[1] = true
                8 -> nanpouArray[2] = true
                9 -> seihouArray[0] = true
                10 -> seihouArray[1] = true
                11 -> seihouArray[2] = true
                12 -> hoppouArray[0] = true
            }
        }

        val yearShi = KanShi.valueOf("Shi$yearShiNo").value
        val monthShi = KanShi.valueOf("Shi$monthShiNo").value
        val dayShi = KanShi.valueOf("Shi$dayShiNo").value
        val touhouSani = context.getString(R.string.isouhou_housani_tou_text)
        val nanpouSani = context.getString(R.string.isouhou_housani_nan_text)
        val seihouSani = context.getString(R.string.isouhou_housani_sei_text)
        val hoppouSani = context.getString(R.string.isouhou_housani_hoku_text)
        var result = ""

        if (touhouArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $touhouSani"
        } else if (nanpouArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $nanpouSani"
        } else if (seihouArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $seihouSani"
        } else if (hoppouArray.count { it } == 3) {
            result = "$dayShi-$monthShi-$yearShi  $hoppouSani"
        }

        return result
    }
}