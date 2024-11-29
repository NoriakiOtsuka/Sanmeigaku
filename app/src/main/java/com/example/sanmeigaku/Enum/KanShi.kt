package com.example.sanmeigaku.Enum

/**
 * Constant list of kan-shi
 */
enum class KanShi(val value: String) {
    Kan0("　"),
    Kan1("甲"),
    Kan2("乙"),
    Kan3("丙"),
    Kan4("丁"),
    Kan5("戊"),
    Kan6("己"),
    Kan7("庚"),
    Kan8("辛"),
    Kan9("壬"),
    Kan10("癸"),
    Shi1("子"),
    Shi2("丑"),
    Shi3("寅"),
    Shi4("卯"),
    Shi5("辰"),
    Shi6("巳"),
    Shi7("午"),
    Shi8("未"),
    Shi9("申"),
    Shi10("酉"),
    Shi11("戌"),
    Shi12("亥")
}

/**
 * Constant list of zoukan
 */
enum class ZouKan(val shogen: String, val chugen: String, val hongen: String, val shoNo: Int, val chuNo: Int, val honNo: Int) {
    ZouKan1("", "", "癸", 0, 0, 10),
    ZouKan2("癸", "辛", "己", 10, 8, 6),
    ZouKan3("戊", "丙", "甲", 5, 3, 1),
    ZouKan4("", "", "乙", 0, 0, 2),
    ZouKan5("乙", "癸", "戊", 2, 10, 5),
    ZouKan6("戊", "庚", "丙", 5, 7, 3),
    ZouKan7("", "己", "丁", 0, 6, 4),
    ZouKan8("丁", "乙", "己", 4, 2, 6),
    ZouKan9("戊", "壬", "庚", 5, 9, 7),
    ZouKan10("", "", "辛", 0, 0, 8),
    ZouKan11("辛", "丁", "戊", 8, 4, 5),
    ZouKan12("", "甲", "壬", 0, 1, 9)
}