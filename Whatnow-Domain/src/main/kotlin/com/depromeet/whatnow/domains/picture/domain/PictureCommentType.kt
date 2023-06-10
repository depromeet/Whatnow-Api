package com.depromeet.whatnow.domains.picture.domain


enum class PictureCommentType(val value: String) {
    // Can LATE
    RUNNING("달려가는 중️"),
    GASPING("헐레벌떡"),
    LEAVE_SOME("남겨놔!"),
    WAIT_A_BIT("조금만 기다려"),
    SORRY_LATE("늦어서 미안해"),

    // Can WAIT
    WHAT_ARE_YOU_DOING("뭐해 심심해"),
    WHAT_TIME_IS_IT_NOW("지금이 몇시야!"),
    DID_YOU_COME("왔나..?"),
    I_LL_EAT_FIRST("먼저 먹을게~"),
    WHERE_ARE_YOU("너 어디야?")
}