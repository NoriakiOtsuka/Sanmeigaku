package com.example.sanmeigaku.Util

class Utility {
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
}