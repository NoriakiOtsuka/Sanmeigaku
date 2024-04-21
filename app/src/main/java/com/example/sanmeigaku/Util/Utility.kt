package com.example.sanmeigaku.Util

class Utility {
    /**
     * Get zou-kan number
     */
    fun getZonKanNo(shiNo: Int, diffStartDay: Int): Int {
        var num = 0
        when (shiNo) {
            1 -> {
                num = 10
            }
            2 -> {
                num = when (diffStartDay) {
                    in 0..8 -> 10
                    in 9..11 -> 8
                    else -> 6
                }
            }
            3 -> {
                num = when (diffStartDay) {
                    in 0..6 -> 5
                    in 7..13 -> 3
                    else -> 1
                }
            }
            4 -> {
                num = 2
            }
            5 -> {
                num = when (diffStartDay) {
                    in 0..8 -> 2
                    in 9..11 -> 10
                    else -> 5
                }
            }
            6 -> {
                num = when (diffStartDay) {
                    in 0..4 -> 5
                    in 5..13 -> 7
                    else -> 3
                }
            }
            7 -> {
                num = when (diffStartDay) {
                    in 0..18 -> 6
                    else -> 4
                }
            }
            8 -> {
                num = when (diffStartDay) {
                    in 0..8 -> 4
                    in 9..11 -> 2
                    else -> 6
                }
            }
            9 -> {
                num = when (diffStartDay) {
                    in 0..9 -> 5
                    in 10..12 -> 9
                    else -> 7
                }
            }
            10 -> {
                num = 8
            }
            11 -> {
                num = when (diffStartDay) {
                    in 0..8 -> 8
                    in 9..11 -> 4
                    else -> 5
                }
            }
            12 -> {
                num = when (diffStartDay) {
                    in 0..11 -> 1
                    else -> 9
                }
            }
        }

        return num
    }

    /**
     * Get zou-kan type from sho-gen, chu-gen, hon-gen
     */
    fun getZonKanType(shiNo: Int, diffStartDay: Int): Int {
        var type = 0
        when (shiNo) {
            1 -> {
                type = 3
            }
            2 -> {
                type = when (diffStartDay) {
                    in 0..8 -> 1
                    in 9..11 -> 2
                    else -> 3
                }
            }
            3 -> {
                type = when (diffStartDay) {
                    in 0..6 -> 1
                    in 7..13 -> 2
                    else -> 3
                }
            }
            4 -> {
                type = 3
            }
            5 -> {
                type = when (diffStartDay) {
                    in 0..8 -> 1
                    in 9..11 -> 2
                    else -> 3
                }
            }
            6 -> {
                type = when (diffStartDay) {
                    in 0..4 -> 1
                    in 5..13 -> 2
                    else -> 3
                }
            }
            7 -> {
                type = when (diffStartDay) {
                    in 0..18 -> 2
                    else -> 3
                }
            }
            8 -> {
                type = when (diffStartDay) {
                    in 0..8 -> 1
                    in 9..11 -> 2
                    else -> 3
                }
            }
            9 -> {
                type = when (diffStartDay) {
                    in 0..9 -> 1
                    in 10..12 -> 2
                    else -> 3
                }
            }
            10 -> {
                type = 3
            }
            11 -> {
                type = when (diffStartDay) {
                    in 0..8 -> 1
                    in 9..11 -> 2
                    else -> 3
                }
            }
            12 -> {
                type = when (diffStartDay) {
                    in 0..11 -> 2
                    else -> 3
                }
            }
        }

        return type
    }

    /**
     * Get main star number
     */
    fun getMainStarNo(dayKanNo: Int, kanNo: Int): Int {
        var num = 0
        when (dayKanNo) {
            1 -> {
                when (kanNo) {
                    1 -> num = 1
                    2 -> num = 2
                    3 -> num = 3
                    4 -> num = 4
                    5 -> num = 5
                    6 -> num = 6
                    7 -> num = 7
                    8 -> num = 8
                    9 -> num = 9
                    10 -> num = 10
                }
            }
            2 -> {
                when (kanNo) {
                    1 -> num = 2
                    2 -> num = 1
                    3 -> num = 4
                    4 -> num = 3
                    5 -> num = 6
                    6 -> num = 5
                    7 -> num = 8
                    8 -> num = 7
                    9 -> num = 10
                    10 -> num = 9
                }
            }
            3 -> {
                when (kanNo) {
                    1 -> num = 9
                    2 -> num = 10
                    3 -> num = 1
                    4 -> num = 2
                    5 -> num = 3
                    6 -> num = 4
                    7 -> num = 5
                    8 -> num = 6
                    9 -> num = 7
                    10 -> num = 8
                }
            }
            4 -> {
                when (kanNo) {
                    1 -> num = 10
                    2 -> num = 9
                    3 -> num = 2
                    4 -> num = 1
                    5 -> num = 4
                    6 -> num = 3
                    7 -> num = 6
                    8 -> num = 5
                    9 -> num = 8
                    10 -> num = 7
                }
            }
            5 -> {
                when (kanNo) {
                    1 -> num = 7
                    2 -> num = 8
                    3 -> num = 9
                    4 -> num = 10
                    5 -> num = 1
                    6 -> num = 2
                    7 -> num = 3
                    8 -> num = 4
                    9 -> num = 5
                    10 -> num = 6
                }
            }
            6 -> {
                when (kanNo) {
                    1 -> num = 8
                    2 -> num = 7
                    3 -> num = 10
                    4 -> num = 9
                    5 -> num = 2
                    6 -> num = 1
                    7 -> num = 4
                    8 -> num = 3
                    9 -> num = 6
                    10 -> num = 5
                }
            }
            7 -> {
                when (kanNo) {
                    1 -> num = 5
                    2 -> num = 6
                    3 -> num = 7
                    4 -> num = 8
                    5 -> num = 9
                    6 -> num = 10
                    7 -> num = 1
                    8 -> num = 2
                    9 -> num = 3
                    10 -> num = 4
                }
            }
            8 -> {
                when (kanNo) {
                    1 -> num = 6
                    2 -> num = 5
                    3 -> num = 8
                    4 -> num = 7
                    5 -> num = 10
                    6 -> num = 9
                    7 -> num = 2
                    8 -> num = 1
                    9 -> num = 4
                    10 -> num = 3
                }
            }
            9 -> {
                when (kanNo) {
                    1 -> num = 3
                    2 -> num = 4
                    3 -> num = 5
                    4 -> num = 6
                    5 -> num = 7
                    6 -> num = 8
                    7 -> num = 9
                    8 -> num = 10
                    9 -> num = 1
                    10 -> num = 2
                }
            }
            10 -> {
                when (kanNo) {
                    1 -> num = 4
                    2 -> num = 3
                    3 -> num = 6
                    4 -> num = 5
                    5 -> num = 8
                    6 -> num = 7
                    7 -> num = 10
                    8 -> num = 9
                    9 -> num = 2
                    10 -> num = 1
                }
            }
        }

        return num
    }

    /**
     * Get second star number
     */
    fun getSecondStarNo(dayKanNo: Int, shiNo: Int): Int {
        var num = 0
        when (dayKanNo) {
            1 -> {
                when (shiNo) {
                    1 -> num = 4
                    2 -> num = 5
                    3 -> num = 6
                    4 -> num = 7
                    5 -> num = 8
                    6 -> num = 9
                    7 -> num = 10
                    8 -> num = 11
                    9 -> num = 12
                    10 -> num = 1
                    11 -> num = 2
                    12 -> num = 3
                }
            }
            2 -> {
                when (shiNo) {
                    1 -> num = 9
                    2 -> num = 8
                    3 -> num = 7
                    4 -> num = 6
                    5 -> num = 5
                    6 -> num = 4
                    7 -> num = 3
                    8 -> num = 2
                    9 -> num = 1
                    10 -> num = 12
                    11 -> num = 11
                    12 -> num = 10
                }
            }
            3 -> {
                when (shiNo) {
                    1 -> num = 1
                    2 -> num = 2
                    3 -> num = 3
                    4 -> num = 4
                    5 -> num = 5
                    6 -> num = 6
                    7 -> num = 7
                    8 -> num = 8
                    9 -> num = 9
                    10 -> num = 10
                    11 -> num = 11
                    12 -> num = 12
                }
            }
            4 -> {
                when (shiNo) {
                    1 -> num = 12
                    2 -> num = 11
                    3 -> num = 10
                    4 -> num = 9
                    5 -> num = 8
                    6 -> num = 7
                    7 -> num = 6
                    8 -> num = 5
                    9 -> num = 4
                    10 -> num = 3
                    11 -> num = 2
                    12 -> num = 1
                }
            }
            5 -> {
                when (shiNo) {
                    1 -> num = 1
                    2 -> num = 2
                    3 -> num = 3
                    4 -> num = 4
                    5 -> num = 5
                    6 -> num = 6
                    7 -> num = 7
                    8 -> num = 8
                    9 -> num = 9
                    10 -> num = 10
                    11 -> num = 11
                    12 -> num = 12
                }
            }
            6 -> {
                when (shiNo) {
                    1 -> num = 12
                    2 -> num = 11
                    3 -> num = 10
                    4 -> num = 9
                    5 -> num = 8
                    6 -> num = 7
                    7 -> num = 6
                    8 -> num = 5
                    9 -> num = 4
                    10 -> num = 3
                    11 -> num = 2
                    12 -> num = 1
                }
            }
            7 -> {
                when (shiNo) {
                    1 -> num = 10
                    2 -> num = 11
                    3 -> num = 12
                    4 -> num = 1
                    5 -> num = 2
                    6 -> num = 3
                    7 -> num = 4
                    8 -> num = 5
                    9 -> num = 6
                    10 -> num = 7
                    11 -> num = 8
                    12 -> num = 9
                }
            }
            8 -> {
                when (shiNo) {
                    1 -> num = 3
                    2 -> num = 2
                    3 -> num = 1
                    4 -> num = 12
                    5 -> num = 11
                    6 -> num = 10
                    7 -> num = 9
                    8 -> num = 8
                    9 -> num = 7
                    10 -> num = 6
                    11 -> num = 5
                    12 -> num = 4
                }
            }
            9 -> {
                when (shiNo) {
                    1 -> num = 7
                    2 -> num = 8
                    3 -> num = 9
                    4 -> num = 10
                    5 -> num = 11
                    6 -> num = 12
                    7 -> num = 1
                    8 -> num = 2
                    9 -> num = 3
                    10 -> num = 4
                    11 -> num = 5
                    12 -> num = 6
                }
            }
            10 -> {
                when (shiNo) {
                    1 -> num = 6
                    2 -> num = 5
                    3 -> num = 4
                    4 -> num = 3
                    5 -> num = 2
                    6 -> num = 1
                    7 -> num = 12
                    8 -> num = 11
                    9 -> num = 10
                    10 -> num = 9
                    11 -> num = 8
                    12 -> num = 7
                }
            }
        }

        return num;
    }

    /**
     * Get shukumei tenchusatsu items array
     */
    fun getTenchusatsuArray(yearNo: Int, monthNo: Int, dayNo: Int): BooleanArray {
        val param1 = when (dayNo) {
            in 1..10 -> 6
            in 11..20 -> 5
            in 21..30 -> 4
            in 31..40 -> 3
            in 41..50 -> 2
            in 51..60 -> 1
            else -> 0
        }
        val param2 = when (yearNo) {
            in 1..10 -> 6
            in 11..20 -> 5
            in 21..30 -> 4
            in 31..40 -> 3
            in 41..50 -> 2
            in 51..60 -> 1
            else -> 0
        }

        val yearShiNo = yearNo.minus(1).rem(12) + 1
        val monthShiNo = monthNo.minus(1).rem(12) + 1
        val dayShiNo = dayNo.minus(1).rem(12) + 1
        val boolArray = booleanArrayOf(false, false, false, false, false, false, false, false)

        /** 生年・生月天中殺 */
        when (param1) {
            1 -> {
                when (yearShiNo) {
                    1, 2 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    1, 2 -> boolArray[1] = true
                }
            }
            2 -> {
                when (yearShiNo) {
                    3, 4 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    3, 4 -> boolArray[1] = true
                }
            }
            3 -> {
                when (yearShiNo) {
                    5, 6 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    5, 6 -> boolArray[1] = true
                }
            }
            4 -> {
                when (yearShiNo) {
                    7, 8 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    7, 8 -> boolArray[1] = true
                }
            }
            5 -> {
                when (yearShiNo) {
                    9, 10 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    9, 10 -> boolArray[1] = true
                }
            }
            6 -> {
                when (yearShiNo) {
                    11, 12 -> boolArray[0] = true
                }
                when (monthShiNo) {
                    11, 12 -> boolArray[1] = true
                }
            }
        }

        /** 生日中殺 */
        when (param2) {
            1 -> {
                when (dayShiNo) {
                    1, 2 -> boolArray[2] = true
                }
            }
            2 -> {
                when (dayShiNo) {
                    3, 4 -> boolArray[2] = true
                }
            }
            3 -> {
                when (dayShiNo) {
                    5, 6 -> boolArray[2] = true
                }
            }
            4 -> {
                when (dayShiNo) {
                    7, 8 -> boolArray[2] = true
                }
            }
            5 -> {
                when (dayShiNo) {
                    9, 10 -> boolArray[2] = true
                }
            }
            6 -> {
                when (dayShiNo) {
                    11, 12 -> boolArray[2] = true
                }
            }
        }

        /** 日座天中殺 */
        if ((dayNo == 11) || (dayNo == 12))
            boolArray[3] = true

        /** 宿命二中殺 */
        if (boolArray[0] && boolArray[1]) {
            boolArray[0] = false
            boolArray[1] = false
            boolArray[4] = true
        }

        /** 全天中殺 */
        if (boolArray[3] && boolArray[4]) {
            boolArray[3] = false
            boolArray[4] = false
            boolArray[5] = true
        }

        /** 互換天中殺 */
        if (boolArray[0] && boolArray[2]) {
            boolArray[0] = false
            boolArray[2] = false
            boolArray[6] = true
        }

        /** 日居天中殺 */
        if ((dayNo == 41) || (dayNo == 42))
            boolArray[7] = true

        return boolArray
    }

    /**
     * Get ijo kan-shi number
     */
    fun getIjokanshiNo(no: Int): Int {
        val num = when (no) {
            11, 12, 37, 48, 54 ->  1
            18, 19, 24, 25, 30, 36 ->  2
            1, 41 ->  3
            22, 42 ->  4
            53 ->  5
            14, 44 ->  6
            5 ->  7
            6, 46 ->  8
            17, 47 ->  9
            8, 38 ->  10
            29, 49 ->  11
            10, 60 ->  12
            23 ->  13
            35 ->  14
            else -> 0
        }

        return num
    }
}