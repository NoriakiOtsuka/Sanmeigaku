package com.example.sanmeigaku.Enum

enum class MainStar(val id: Int, val value: String) {
    Main1(1, "貫索星"),
    Main2(2, "石門星"),
    Main3(3, "鳳閣星"),
    Main4(4, "調舒星"),
    Main5(5, "禄存星"),
    Main6(6, "司禄星"),
    Main7(7, "車騎星"),
    Main8(8, "牽牛星"),
    Main9(9, "龍高星"),
    Main10(10, "玉堂星")
}

enum class SecondStar(val id: Int, val value: String, val score: Int) {
    Second1(1, "天報星", 3),
    Second2(2, "天印星", 6),
    Second3(3, "天貴星", 9),
    Second4(4, "天恍星", 7),
    Second5(5, "天南星", 10),
    Second6(6, "天禄星", 11),
    Second7(7, "天将星", 12),
    Second8(8, "天堂星", 8),
    Second9(9, "天胡星", 4),
    Second10(10, "天極星", 2),
    Second11(11, "天庫星", 5),
    Second12(12, "天馳星", 1)
}