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
        val boolArray = verifySangouKaikyoku(yearShiNo, monthShiNo, dayShiNo)
        val hasMoku = boolArray[0]
        val hasKa = boolArray[1]
        val hasGon = boolArray[2]
        val hasSui = boolArray[3]
        val yearShi = KanShi.valueOf("Shi$yearShiNo").value
        val monthShi = KanShi.valueOf("Shi$monthShiNo").value
        val dayShi = KanShi.valueOf("Shi$dayShiNo").value
        val mokuSangou = context.getString(R.string.isouhou_sangoukaikyoku_moku_text)
        val kaSangou = context.getString(R.string.isouhou_sangoukaikyoku_ka_text)
        val gonSangou = context.getString(R.string.isouhou_sangoukaikyoku_gon_text)
        val suiSangou = context.getString(R.string.isouhou_sangoukaikyoku_sui_text)
        var result = ""

        if (hasMoku) {
            result = "$dayShi-$monthShi-$yearShi  $mokuSangou"
        } else if (hasKa) {
            result = "$dayShi-$monthShi-$yearShi  $kaSangou"
        } else if (hasGon) {
            result = "$dayShi-$monthShi-$yearShi  $gonSangou"
        } else if (hasSui) {
            result = "$dayShi-$monthShi-$yearShi  $suiSangou"
        }

        return result
    }

    /**
     * Verify what type of SangouKaikyoku is included
     */
    private fun verifySangouKaikyoku(yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): BooleanArray {
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

        val boolArray = booleanArrayOf(false, false, false, false)
        if (mokuArray.count { it } == 3) {
            boolArray[0] = true
        } else if (kaArray.count { it } == 3) {
            boolArray[1] = true
        } else if (gonArray.count { it } == 3) {
            boolArray[2] = true
        } else if (suiArray.count { it } == 3) {
            boolArray[3] = true
        }

        return boolArray
    }

    /**
     * Get Hankai assessment result
     */
    fun getHankai(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val hasSangouKaikyoku = verifySangouKaikyoku(yearShiNo, monthShiNo, dayShiNo).contains(true)
        if (hasSangouKaikyoku)
            return ""

        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val mokuArray = IntArray(3)
        val kaArray = IntArray(3)
        val gonArray = IntArray(3)
        val suiArray = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 5, 9 -> suiArray[index] = i
                2, 6, 10 -> gonArray[index] = i
                3, 7, 11 -> kaArray[index] = i
                4, 8, 12 -> mokuArray[index] = i
            }
        }

        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"
        val mokuHankai = context.getString(R.string.isouhou_hankai_moku_text)
        val kaHankai = context.getString(R.string.isouhou_hankai_ka_text)
        val gonHankai = context.getString(R.string.isouhou_hankai_gon_text)
        val suiHankai = context.getString(R.string.isouhou_hankai_sui_text)
        var result = ""

        if (mokuArray.count { it == 0 } < 2) {
            val mokuMaxNo = mokuArray.maxBy { it }
            val mokuMinNo = mokuArray.filter { it > 0 }.minBy { it }
            if (mokuMaxNo != mokuMinNo) {
                if (mokuArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (mokuArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (mokuArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $mokuHankai"
            }
        } else if (kaArray.count { it == 0 } < 2) {
            val kaMaxNo = kaArray.maxBy { it }
            val kaMinNo = kaArray.filter { it > 0 }.minBy { it }
            if (kaMaxNo != kaMinNo) {
                if (kaArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (kaArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (kaArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $kaHankai"
            }
        } else if (gonArray.count { it == 0 } < 2) {
            val gonMaxNo = gonArray.maxBy { it }
            val gonMinNo = gonArray.filter { it > 0 }.minBy { it }
            if (gonMaxNo != gonMinNo) {
                if (gonArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (gonArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (gonArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $gonHankai"
            }
        } else if (suiArray.count { it == 0 } < 2) {
            val suiMaxNo = suiArray.maxBy { it }
            val suiMinNo = suiArray.filter { it > 0 }.minBy { it }
            if (suiMaxNo != suiMinNo) {
                if (suiArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (suiArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (suiArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $suiHankai"
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

    /**
     * Get Taichu assessment result
     */
    fun getTaichu(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val shisei1Array = IntArray(3)
        val shisei2Array = IntArray(3)
        val shise1Array = IntArray(3)
        val shise2Array = IntArray(3)
        val shiko1Array = IntArray(3)
        val shiko2Array = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 7 -> shisei1Array[index] = i
                2, 8 -> shiko1Array[index] = i
                3, 9 -> shise1Array[index] = i
                4, 10 -> shisei2Array[index] = i
                5, 11 -> shiko2Array[index] = i
                6, 12 -> shise2Array[index] = i
            }
        }

        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"
        val shiseiTaichu = context.getString(R.string.isouhou_taichu_shisei_text)
        val shiseTaichu = context.getString(R.string.isouhou_taichu_shise_text)
        val shikoTaichu = context.getString(R.string.isouhou_taichu_shiko_text)
        var result = ""

        if (shisei1Array.count { it == 0 } < 2) {
            val shisei1MaxNo = shisei1Array.maxBy { it }
            val shisei1MinNo = shisei1Array.filter { it > 0 }.minBy { it }
            if (shisei1MaxNo != shisei1MinNo) {
                if (shisei1Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shisei1Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shisei1Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shiseiTaichu"
            }
        } else if (shisei2Array.count { it == 0 } < 2) {
            val shisei2MaxNo = shisei2Array.maxBy { it }
            val shisei2MinNo = shisei2Array.filter { it > 0 }.minBy { it }
            if (shisei2MaxNo != shisei2MinNo) {
                if (shisei2Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shisei2Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shisei2Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shiseiTaichu"
            }
        } else if (shise1Array.count { it == 0 } < 2) {
            val shise1MaxNo = shise1Array.maxBy { it }
            val shise1MinNo = shise1Array.filter { it > 0 }.minBy { it }
            if (shise1MaxNo != shise1MinNo) {
                if (shise1Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shise1Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shise1Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shiseTaichu"
            }
        } else if (shise2Array.count { it == 0 } < 2) {
            val shise2MaxNo = shise2Array.maxBy { it }
            val shise2MinNo = shise2Array.filter { it > 0 }.minBy { it }
            if (shise2MaxNo != shise2MinNo) {
                if (shise2Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shise2Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shise2Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shiseTaichu"
            }
        } else if (shiko1Array.count { it == 0 } < 2) {
            val shiko1MaxNo = shiko1Array.maxBy { it }
            val shiko1MinNo = shiko1Array.filter { it > 0 }.minBy { it }
            if (shiko1MaxNo != shiko1MinNo) {
                if (shiko1Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shiko1Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shiko1Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shikoTaichu"
            }
        } else if (shiko2Array.count { it == 0 } < 2) {
            val shiko2MaxNo = shiko2Array.maxBy { it }
            val shiko2MinNo = shiko2Array.filter { it > 0 }.minBy { it }
            if (shiko2MaxNo != shiko2MinNo) {
                if (shiko2Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (shiko2Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (shiko2Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $shikoTaichu"
            }
        }

        return result
    }

    /**
     * Get Kei assessment result
     */
    fun getKei(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val ji1Array = IntArray(3)
        val ji2Array = IntArray(3)
        val ji3Array = IntArray(3)
        val ji4Array = IntArray(3)
        val oukiArray = IntArray(3)
        val seikiArray = IntArray(3)
        val kokiArray = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 4 -> oukiArray[index] = i
                2, 8, 11 -> kokiArray[index] = i
                3, 6, 9 -> seikiArray[index] = i
                5 -> ji1Array[index] = i
                7 -> ji2Array[index] = i
                10 -> ji3Array[index] = i
                12 -> ji4Array[index] = i
            }
        }

        val jiKei = context.getString(R.string.isouhou_kei_ji_text)
        val oukiKei = context.getString(R.string.isouhou_kei_ouki_text)
        val seikiKei = context.getString(R.string.isouhou_kei_seiki_text)
        val kokiKei = context.getString(R.string.isouhou_kei_koki_text)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"
        var result = ""

        if (ji1Array.count { it == 0 } < 2) {
            if (ji1Array[0] != 0)
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            if (ji1Array[1] != 0)
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            if (ji1Array[2] != 0)
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            result = "$dayShi-$monthShi-$yearShi  $jiKei"
        } else if (ji2Array.count { it == 0 } < 2) {
            if (ji2Array[0] != 0)
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            if (ji2Array[1] != 0)
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            if (ji2Array[2] != 0)
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            result = "$dayShi-$monthShi-$yearShi  $jiKei"
        } else if (ji3Array.count { it == 0 } < 2) {
            if (ji3Array[0] != 0)
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            if (ji3Array[1] != 0)
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            if (ji3Array[2] != 0)
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            result = "$dayShi-$monthShi-$yearShi  $jiKei"
        } else if (ji4Array.count { it == 0 } < 2) {
            if (ji4Array[0] != 0)
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            if (ji4Array[1] != 0)
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            if (ji4Array[2] != 0)
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            result = "$dayShi-$monthShi-$yearShi  $jiKei"
        } else if (oukiArray.count { it == 0 } < 2) {
            val oukiMaxNo = oukiArray.maxBy { it }
            val oukiMinNo = oukiArray.filter { it > 0 }.minBy { it }
            if (oukiMaxNo != oukiMinNo) {
                if (oukiArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (oukiArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (oukiArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $oukiKei"
            }
        } else if (seikiArray.count { it == 0 } < 2) {
            val seikiMaxNo = seikiArray.maxBy { it }
            val seikiMinNo = seikiArray.filter { it > 0 }.minBy { it }
            if (seikiMaxNo != seikiMinNo) {
                if (seikiArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (seikiArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (seikiArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $seikiKei"
            }
        } else if (kokiArray.count { it == 0 } < 2) {
            val kokiMaxNo = kokiArray.maxBy { it }
            val kokiMinNo = kokiArray.filter { it > 0 }.minBy { it }
            if (kokiMaxNo != kokiMinNo) {
                if (kokiArray[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (kokiArray[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (kokiArray[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi  $kokiKei"
            }
        }

        return result
    }

    /**
     * Get Ha assessment result
     */
    fun getHa(yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val ha1Array = IntArray(3)
        val ha2Array = IntArray(3)
        val ha3Array = IntArray(3)
        val ha4Array = IntArray(3)
        val ha5Array = IntArray(3)
        val ha6Array = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 10 -> ha1Array[index] = i
                2, 5 -> ha2Array[index] = i
                3, 12 -> ha3Array[index] = i
                4, 7 -> ha4Array[index] = i
                6, 9 -> ha5Array[index] = i
                8, 11 -> ha6Array[index] = i
            }
        }

        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"
        var result = ""

        if (ha1Array.count { it == 0 } < 2) {
            val ha1MaxNo = ha1Array.maxBy { it }
            val ha1MinNo = ha1Array.filter { it > 0 }.minBy { it }
            if (ha1MaxNo != ha1MinNo) {
                if (ha1Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha1Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha1Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        } else if (ha2Array.count { it == 0 } < 2) {
            val ha2MaxNo = ha2Array.maxBy { it }
            val ha2MinNo = ha2Array.filter { it > 0 }.minBy { it }
            if (ha2MaxNo != ha2MinNo) {
                if (ha2Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha2Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha2Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        } else if (ha3Array.count { it == 0 } < 2) {
            val ha3MaxNo = ha3Array.maxBy { it }
            val ha3MinNo = ha3Array.filter { it > 0 }.minBy { it }
            if (ha3MaxNo != ha3MinNo) {
                if (ha3Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha3Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha3Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        } else if (ha4Array.count { it == 0 } < 2) {
            val ha4MaxNo = ha4Array.maxBy { it }
            val ha4MinNo = ha4Array.filter { it > 0 }.minBy { it }
            if (ha4MaxNo != ha4MinNo) {
                if (ha4Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha4Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha4Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        } else if (ha5Array.count { it == 0 } < 2) {
            val ha5MaxNo = ha5Array.maxBy { it }
            val ha5MinNo = ha5Array.filter { it > 0 }.minBy { it }
            if (ha5MaxNo != ha5MinNo) {
                if (ha5Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha5Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha5Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        } else if (ha6Array.count { it == 0 } < 2) {
            val ha6MaxNo = ha6Array.maxBy { it }
            val ha6MinNo = ha6Array.filter { it > 0 }.minBy { it }
            if (ha6MaxNo != ha6MinNo) {
                if (ha6Array[0] != 0)
                    yearShi = KanShi.valueOf("Shi$yearShiNo").value
                if (ha6Array[1] != 0)
                    monthShi = KanShi.valueOf("Shi$monthShiNo").value
                if (ha6Array[2] != 0)
                    dayShi = KanShi.valueOf("Shi$dayShiNo").value
                result = "$dayShi-$monthShi-$yearShi"
            }
        }

        return result
    }

    /**
     * Get Gai assessment result
     */
    fun getGai(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val arrayNo = intArrayOf(yearShiNo, monthShiNo, dayShiNo)
        val hoppouArray = IntArray(3)
        val nanpouArray = IntArray(3)
        val touhouArray = IntArray(3)
        val chuouArray = IntArray(3)
        val seihouArray = IntArray(3)
        val tenjikuArray = IntArray(3)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1, 8 -> hoppouArray[index] = i
                2, 7 -> nanpouArray[index] = i
                3, 6 -> touhouArray[index] = i
                4, 5 -> chuouArray[index] = i
                9, 12 -> seihouArray[index] = i
                10, 11 -> tenjikuArray[index] = i
            }
        }

        val hoppouGai = context.getString(R.string.isouhou_gai_hoku_text)
        val nanpouGai = context.getString(R.string.isouhou_gai_nan_text)
        val touhouGai = context.getString(R.string.isouhou_gai_tou_text)
        val chuouGai = context.getString(R.string.isouhou_gai_chu_text)
        val seihouGai = context.getString(R.string.isouhou_gai_sei_text)
        val tenjikuGai = context.getString(R.string.isouhou_gai_ten_text)
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
                result = "$dayShi-$monthShi-$yearShi  $hoppouGai"
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
                result = "$dayShi-$monthShi-$yearShi  $nanpouGai"
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
                result = "$dayShi-$monthShi-$yearShi  $touhouGai"
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
                result = "$dayShi-$monthShi-$yearShi  $chuouGai"
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
                result = "$dayShi-$monthShi-$yearShi  $seihouGai"
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
                result = "$dayShi-$monthShi-$yearShi  $tenjikuGai"
            }
        }

        return result
    }
}