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

        val year_month = getHankaiNo(yearShiNo, monthShiNo)
        val month_day = getHankaiNo(monthShiNo, dayShiNo)
        val day_year = getHankaiNo(dayShiNo, yearShiNo)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        when (year_month) {
            in 1..5 -> {
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            }
        }
        when (month_day) {
            in 1..5 -> {
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            }
        }
        when (day_year) {
            in 1..5 -> {
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            }
        }

        val intArray = intArrayOf(year_month, month_day, day_year)
        val mokuHankai = context.getString(R.string.isouhou_hankai_moku_text)
        val kaHankai = context.getString(R.string.isouhou_hankai_ka_text)
        val gonHankai = context.getString(R.string.isouhou_hankai_gon_text)
        val suiHankai = context.getString(R.string.isouhou_hankai_sui_text)
        var result = ""

        if (intArray.contains(1)) {
            result = "$dayShi-$monthShi-$yearShi  $mokuHankai"
        } else if (intArray.contains(2)) {
            result = "$dayShi-$monthShi-$yearShi  $kaHankai"
        } else if (intArray.contains(4)) {
            result = "$dayShi-$monthShi-$yearShi  $gonHankai"
        } else if (intArray.contains(5)) {
            result = "$dayShi-$monthShi-$yearShi  $suiHankai"
        }

        return result
    }

    /**
     * Get Hankai parameter number
     * @return 1:木性, 2:火性, 4:金性, 5:水性
     */
    fun getHankaiNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
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

        val mokuCount = mokuArray.count { it }
        val kaCount = kaArray.count { it }
        val gonCount = gonArray.count { it }
        val suiCount = suiArray.count { it }
        var num = 0

        if (mokuCount == 2) {
            num = 1
        } else if (kaCount == 2) {
            num = 2
        } else if (gonCount == 2) {
            num = 4
        } else if (suiCount == 2) {
            num = 5
        }

        return num
    }

    /**
     * Get Shigou assessment result
     */
    fun getShigou(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val year_month = getShigouNo(yearShiNo, monthShiNo)
        val month_day = getShigouNo(monthShiNo, dayShiNo)
        val day_year = getShigouNo(dayShiNo, yearShiNo)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        when (year_month) {
            in 1..7 -> {
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            }
        }
        when (month_day) {
            in 1..7 -> {
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            }
        }
        when (day_year) {
            in 1..7 -> {
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            }
        }

        val intArray = intArrayOf(year_month, month_day, day_year)
        val touhouShigou = context.getString(R.string.isouhou_shigou_tou_text)
        val nanpouShigou = context.getString(R.string.isouhou_shigou_nan_text)
        val seihouShigou = context.getString(R.string.isouhou_shigou_sei_text)
        val hoppouShigou = context.getString(R.string.isouhou_shigou_hoku_text)
        val chuouShigou = context.getString(R.string.isouhou_shigou_chu_text)
        val tenjikuShigou = context.getString(R.string.isouhou_shigou_ten_text)
        var result = ""

        if (intArray.contains(1)) {
            result = "$dayShi-$monthShi-$yearShi  $touhouShigou"
        } else if (intArray.contains(2)) {
            result = "$dayShi-$monthShi-$yearShi  $nanpouShigou"
        } else if (intArray.contains(4)) {
            result = "$dayShi-$monthShi-$yearShi  $seihouShigou"
        } else if (intArray.contains(5)) {
            result = "$dayShi-$monthShi-$yearShi  $hoppouShigou"
        } else if (intArray.contains(6)) {
            result = "$dayShi-$monthShi-$yearShi  $chuouShigou"
        } else if (intArray.contains(7)) {
            result = "$dayShi-$monthShi-$yearShi  $tenjikuShigou"
        }

        return result
    }

    /**
     * Get Shigou parameter number
     * @return 1:東方支合, 2:南方支合, 4:西方支合, 5:北方支合, 6:中央支合, 7:天軸支合
     */
    fun getShigouNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
        val touhouArray = booleanArrayOf(false, false)
        val nanpouArray = booleanArrayOf(false, false)
        val seihouArray = booleanArrayOf(false, false)
        val hoppouArray = booleanArrayOf(false, false)
        val chuouArray = booleanArrayOf(false, false)
        val tenjikuArray = booleanArrayOf(false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> hoppouArray[0] = true
                2 -> hoppouArray[1] = true
                3 -> touhouArray[0] = true
                4 -> chuouArray[0] = true
                5 -> tenjikuArray[0] = true
                6 -> seihouArray[0] = true
                7 -> nanpouArray[0] = true
                8 -> nanpouArray[1] = true
                9 -> seihouArray[1] = true
                10 -> tenjikuArray[1] = true
                11 -> chuouArray[1] = true
                12 -> touhouArray[1] = true
            }
        }

        val touhouCount = touhouArray.count { it }
        val nanpouCount = nanpouArray.count { it }
        val seihouCount = seihouArray.count { it }
        val hoppouCount = hoppouArray.count { it }
        val chuouCount = chuouArray.count { it }
        val tenjikuCount = tenjikuArray.count { it }
        var num = 0

        if (touhouCount == 2) {
            num = 1
        } else if (nanpouCount == 2) {
            num = 2
        } else if (seihouCount == 2) {
            num = 4
        } else if (hoppouCount == 2) {
            num = 5
        } else if (chuouCount == 2) {
            num = 6
        } else if (tenjikuCount == 2) {
            num = 7
        }

        return num
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
        val year_month = getTaichuNo(yearShiNo, monthShiNo)
        val month_day = getTaichuNo(monthShiNo, dayShiNo)
        val day_year = getTaichuNo(dayShiNo, yearShiNo)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        when (year_month) {
            in 1..3 -> {
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            }
        }
        when (month_day) {
            in 1..3 -> {
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            }
        }
        when (day_year) {
            in 1..3 -> {
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            }
        }

        val intArray = intArrayOf(year_month, month_day, day_year)
        val shiseiTaichu = context.getString(R.string.isouhou_taichu_shisei_text)
        val shiseTaichu = context.getString(R.string.isouhou_taichu_shise_text)
        val shikoTaichu = context.getString(R.string.isouhou_taichu_shiko_text)
        var result = ""

        if (intArray.contains(1)) {
            result = "$dayShi-$monthShi-$yearShi  $shiseiTaichu"
        } else if (intArray.contains(2)) {
            result = "$dayShi-$monthShi-$yearShi  $shiseTaichu"
        } else if (intArray.contains(3)) {
            result = "$dayShi-$monthShi-$yearShi  $shikoTaichu"
        }

        return result
    }

    /**
     * Get Taichu parameter number
     * @return 1:四正, 2:四勢, 3:四庫
     */
    fun getTaichuNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
        val shisei1Array = booleanArrayOf(false, false)
        val shisei2Array = booleanArrayOf(false, false)
        val shise1Array = booleanArrayOf(false, false)
        val shise2Array = booleanArrayOf(false, false)
        val shiko1Array = booleanArrayOf(false, false)
        val shiko2Array = booleanArrayOf(false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> shisei1Array[0] = true
                2 -> shiko1Array[0] = true
                3 -> shise1Array[0] = true
                4 -> shisei2Array[0] = true
                5 -> shiko2Array[0] = true
                6 -> shise2Array[0] = true
                7 -> shisei1Array[1] = true
                8 -> shiko1Array[1] = true
                9 -> shise1Array[1] = true
                10 -> shisei2Array[1] = true
                11 -> shiko2Array[1] = true
                12 -> shise2Array[1] = true
            }
        }

        val shisei1Count = shisei1Array.count { it }
        val shisei2Count = shisei2Array.count { it }
        val shise1Count = shise1Array.count { it }
        val shise2Count = shise2Array.count { it }
        val shiko1Count = shiko1Array.count { it }
        val shiko2Count = shiko2Array.count { it }
        var num = 0

        if ((shisei1Count == 2) || (shisei2Count == 2)) {
            num = 1
        } else if ((shise1Count == 2) || (shise2Count == 2)) {
            num = 2
        } else if ((shiko1Count == 2) || (shiko2Count == 2)) {
            num = 3
        }

        return num
    }

    /**
     * Get Kei assessment result
     */
    fun getKei(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val year_month = getKeiNo(yearShiNo, monthShiNo)
        val month_day = getKeiNo(monthShiNo, dayShiNo)
        val day_year = getKeiNo(dayShiNo, yearShiNo)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        when (year_month) {
            in 1..4 -> {
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            }
        }
        when (month_day) {
            in 1..4 -> {
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            }
        }
        when (day_year) {
            in 1..4 -> {
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            }
        }

        val intArray = intArrayOf(year_month, month_day, day_year)
        val jiKei = context.getString(R.string.isouhou_kei_ji_text)
        val oukiKei = context.getString(R.string.isouhou_kei_ouki_text)
        val seikiKei = context.getString(R.string.isouhou_kei_seiki_text)
        val kokiKei = context.getString(R.string.isouhou_kei_koki_text)
        var result = ""

        if (intArray.contains(1)) {
            result = "$dayShi-$monthShi-$yearShi  $jiKei"
        } else if (intArray.contains(2)) {
            result = "$dayShi-$monthShi-$yearShi  $oukiKei"
        } else if (intArray.contains(3)) {
            result = "$dayShi-$monthShi-$yearShi  $seikiKei"
        } else if (intArray.contains(4)) {
            result = "$dayShi-$monthShi-$yearShi  $kokiKei"
        }

        return result
    }

    /**
     * Get Kei parameter number
     * @return 1:自刑, 2:旺気刑, 3:生貴刑, 4:庫気刑
     */
    fun getKeiNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
        val ji1Array = booleanArrayOf(false, false)
        val ji2Array = booleanArrayOf(false, false)
        val ji3Array = booleanArrayOf(false, false)
        val ji4Array = booleanArrayOf(false, false)
        val oukiArray = booleanArrayOf(false, false)
        val seikiArray = booleanArrayOf(false, false, false)
        val kokiArray = booleanArrayOf(false, false, false)

        for ((index, i) in arrayNo.withIndex()) {
            when (i) {
                1 -> oukiArray[0] = true
                2 -> kokiArray[0] = true
                3 -> seikiArray[0] = true
                4 -> oukiArray[1] = true
                5 -> ji1Array[index] = true
                6 -> seikiArray[1] = true
                7 -> ji2Array[index] = true
                8 -> kokiArray[1] = true
                9 -> seikiArray[2] = true
                10 -> ji3Array[index] = true
                11 -> kokiArray[2] = true
                12 -> ji4Array[index] = true
            }
        }

        val ji1Count = ji1Array.count { it }
        val ji2Count = ji2Array.count { it }
        val ji3Count = ji3Array.count { it }
        val ji4Count = ji4Array.count { it }
        val oukiCount = oukiArray.count { it }
        val seikiCount = seikiArray.count { it }
        val kokiCount = kokiArray.count { it }
        var num = 0

        if ((ji1Count == 2) || (ji2Count == 2) || (ji3Count == 2) || (ji4Count == 2)) {
            num = 1
        } else if (oukiCount == 2) {
            num = 2
        } else if (seikiCount == 2) {
            num = 3
        } else if (kokiCount == 2) {
            num = 4
        }

        return num
    }

    /**
     * Get Ha assessment result
     */
    fun getHa(yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val year_month = getHaNo(yearShiNo, monthShiNo)
        val month_day = getHaNo(monthShiNo, dayShiNo)
        val day_year = getHaNo(dayShiNo, yearShiNo)

        if ((year_month == 0) && (month_day == 0) && (day_year == 0))
            return ""

        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        if (year_month > 0) {
            yearShi = KanShi.valueOf("Shi$yearShiNo").value
            monthShi = KanShi.valueOf("Shi$monthShiNo").value
        }
        if (month_day > 0) {
            monthShi = KanShi.valueOf("Shi$monthShiNo").value
            dayShi = KanShi.valueOf("Shi$dayShiNo").value
        }
        if (day_year > 0) {
            dayShi = KanShi.valueOf("Shi$dayShiNo").value
            yearShi = KanShi.valueOf("Shi$yearShiNo").value
        }

        return "$dayShi-$monthShi-$yearShi"
    }

    /**
     * Get Ha parameter number
     */
    fun getHaNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
        val ha1Array = booleanArrayOf(false, false)
        val ha2Array = booleanArrayOf(false, false)
        val ha3Array = booleanArrayOf(false, false)
        val ha4Array = booleanArrayOf(false, false)
        val ha5Array = booleanArrayOf(false, false)
        val ha6Array = booleanArrayOf(false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> ha1Array[0] = true
                2 -> ha2Array[0] = true
                3 -> ha3Array[0] = true
                4 -> ha4Array[0] = true
                5 -> ha2Array[1] = true
                6 -> ha5Array[0] = true
                7 -> ha4Array[1] = true
                8 -> ha6Array[0] = true
                9 -> ha5Array[1] = true
                10 -> ha1Array[1] = true
                11 -> ha6Array[1] = true
                12 -> ha3Array[1] = true
            }
        }

        val ha1Count = ha1Array.count { it }
        val ha2Count = ha2Array.count { it }
        val ha3Count = ha3Array.count { it }
        val ha4Count = ha4Array.count { it }
        val ha5Count = ha5Array.count { it }
        val ha6Count = ha6Array.count { it }
        var num = 0

        if (ha1Count == 2) {
            num = 1
        } else if (ha2Count == 2) {
            num = 2
        } else if (ha3Count == 2) {
            num = 3
        } else if (ha4Count == 2) {
            num = 4
        } else if (ha5Count == 2) {
            num = 5
        } else if (ha6Count == 2) {
            num = 6
        }

        return num
    }

    /**
     * Get Gai assessment result
     */
    fun getGai(context: Context, yearShiNo: Int, monthShiNo: Int, dayShiNo: Int): String {
        val year_month = getGaiNo(yearShiNo, monthShiNo)
        val month_day = getGaiNo(monthShiNo, dayShiNo)
        val day_year = getGaiNo(dayShiNo, yearShiNo)
        var yearShi = "＿"
        var monthShi = "＿"
        var dayShi = "＿"

        when (year_month) {
            in 1..7 -> {
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
            }
        }
        when (month_day) {
            in 1..7 -> {
                monthShi = KanShi.valueOf("Shi$monthShiNo").value
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
            }
        }
        when (day_year) {
            in 1..7 -> {
                dayShi = KanShi.valueOf("Shi$dayShiNo").value
                yearShi = KanShi.valueOf("Shi$yearShiNo").value
            }
        }

        val intArray = intArrayOf(year_month, month_day, day_year)
        val touhouGai = context.getString(R.string.isouhou_gai_tou_text)
        val nanpouGai = context.getString(R.string.isouhou_gai_nan_text)
        val seihouGai = context.getString(R.string.isouhou_gai_sei_text)
        val hoppouGai = context.getString(R.string.isouhou_gai_hoku_text)
        val chuouGai = context.getString(R.string.isouhou_gai_chu_text)
        val tenjikuGai = context.getString(R.string.isouhou_gai_ten_text)
        var result = ""

        if (intArray.contains(1)) {
            result = "$dayShi-$monthShi-$yearShi  $touhouGai"
        } else if (intArray.contains(2)) {
            result = "$dayShi-$monthShi-$yearShi  $nanpouGai"
        } else if (intArray.contains(4)) {
            result = "$dayShi-$monthShi-$yearShi  $seihouGai"
        } else if (intArray.contains(5)) {
            result = "$dayShi-$monthShi-$yearShi  $hoppouGai"
        } else if (intArray.contains(6)) {
            result = "$dayShi-$monthShi-$yearShi  $chuouGai"
        } else if (intArray.contains(7)) {
            result = "$dayShi-$monthShi-$yearShi  $tenjikuGai"
        }

        return result
    }

    /**
     * Get Gai parameter number
     * @return 1:東方害, 2:南方害, 4:西方害, 5:北方害, 6:中央害, 7:天軸害
     */
    fun getGaiNo(shiNo1: Int, shiNo2: Int): Int {
        val arrayNo = intArrayOf(shiNo1, shiNo2)
        val touhouArray = booleanArrayOf(false, false)
        val nanpouArray = booleanArrayOf(false, false)
        val seihouArray = booleanArrayOf(false, false)
        val hoppouArray = booleanArrayOf(false, false)
        val chuouArray = booleanArrayOf(false, false)
        val tenjikuArray = booleanArrayOf(false, false)

        for (i in arrayNo) {
            when (i) {
                1 -> hoppouArray[0] = true
                2 -> nanpouArray[0] = true
                3 -> touhouArray[0] = true
                4 -> chuouArray[0] = true
                5 -> chuouArray[1] = true
                6 -> touhouArray[1] = true
                7 -> nanpouArray[1] = true
                8 -> hoppouArray[1] = true
                9 -> seihouArray[0] = true
                10 -> tenjikuArray[0] = true
                11 -> tenjikuArray[1] = true
                12 -> seihouArray[1] = true
            }
        }

        val touhouCount = touhouArray.count { it }
        val nanpouCount = nanpouArray.count { it }
        val seihouCount = seihouArray.count { it }
        val hoppouCount = hoppouArray.count { it }
        val chuouCount = chuouArray.count { it }
        val tenjikuCount = tenjikuArray.count { it }
        var num = 0

        if (touhouCount == 2) {
            num = 1
        } else if (nanpouCount == 2) {
            num = 2
        } else if (seihouCount == 2) {
            num = 4
        } else if (hoppouCount == 2) {
            num = 5
         } else if (chuouCount == 2) {
            num = 6
        } else if (tenjikuCount == 2) {
            num = 7
        }

        return num
    }
}