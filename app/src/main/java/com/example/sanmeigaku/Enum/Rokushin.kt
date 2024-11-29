package com.example.sanmeigaku.Enum

enum class RokuRelative(val value: String) {
    Roku0("myself"),
    Roku1("spouse"),
    Roku2("my father"),
    Roku3("my mother"),
    Roku4("paternal grandfather"),
    Roku5("paternal grandmother"),
    Roku6("maternal grandfather"),
    Roku7("maternal grandmother"),
    Roku8("spouse father"),
    Roku9("spouse mother"),
    Roku10("son"),
    Roku11("son's spouse"),
    Roku12("daughter"),
    Roku13("daughter's spouse")
}

enum class RokuMale(val myself: Int, val spouse: Int, val myFather: Int, val myMother: Int, val pgFather: Int, val pgMother: Int, val mgFather: Int, val mgMother: Int,
                      val spouseFather: Int, val spouseMother: Int, val son: Int, val sonSpouse: Int, val daughter: Int, val daughterSpouse: Int) {
    Roku1(1, 6, 5, 10, 9, 4, 2, 7, 8, 3, 7, 2, 8, 3),
    Roku2(2, 7, 4, 9, 6, 1, 3, 8, 1, 6, 10, 5, 9, 4),
    Roku3(3, 8, 7, 2, 1, 6, 4, 9, 10, 5, 9, 4, 10, 5),
    Roku4(4, 9, 6, 1, 8, 3, 5, 10, 3, 8, 2, 7, 1, 6),
    Roku5(5, 10, 9, 4, 3, 8, 6, 1, 2, 7, 1, 6, 2, 7),
    Roku6(6, 1, 8, 3, 10, 5, 7, 2, 5, 10, 4, 9, 3, 8),
    Roku7(7, 2, 1, 6, 5, 10, 8, 3, 4, 9, 3, 8, 4, 9),
    Roku8(8, 3, 10, 5, 2, 7, 9, 4, 7, 2, 6, 1, 5, 10),
    Roku9(9, 4, 3, 8, 7, 2, 10, 5, 6, 1, 5, 10, 6, 1),
    Roku10(10, 5, 2, 7, 4, 9, 1, 6, 9, 4, 8, 3, 7, 2)
}

enum class RokuFemale(val myself: Int, val spouse: Int, val myFather: Int, val myMother: Int, val pgFather: Int, val pgMother: Int, val mgFather: Int, val mgMother: Int,
                    val spouseFather: Int, val spouseMother: Int, val son: Int, val sonSpouse: Int, val daughter: Int, val daughterSpouse: Int) {
    Roku1(1, 6, 5, 10, 9, 4, 2, 7, 8, 3, 4, 9, 3, 8),
    Roku2(2, 7, 4, 9, 6, 1, 3, 8, 1, 6, 3, 8, 4, 9),
    Roku3(3, 8, 7, 2, 1, 6, 4, 9, 10, 5, 6, 1, 5, 10),
    Roku4(4, 9, 6, 1, 8, 3, 5, 10, 3, 8, 5, 10, 6, 1),
    Roku5(5, 10, 9, 4, 3, 8, 6, 1, 2, 7, 8, 3, 7, 2),
    Roku6(6, 1, 8, 3, 10, 5, 7, 2, 5, 10, 7, 2, 8, 3),
    Roku7(7, 2, 1, 6, 5, 10, 8, 3, 4, 9, 10, 5, 9, 4),
    Roku8(8, 3, 10, 5, 2, 7, 9, 4, 7, 2, 9, 4, 10, 5),
    Roku9(9, 4, 3, 8, 7, 2, 10, 5, 6, 1, 2, 7, 1, 6),
    Roku10(10, 5, 2, 7, 4, 9, 1, 6, 9, 4, 1, 6, 2, 7)
}