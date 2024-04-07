package com.example.sanmeigaku.Util

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
}