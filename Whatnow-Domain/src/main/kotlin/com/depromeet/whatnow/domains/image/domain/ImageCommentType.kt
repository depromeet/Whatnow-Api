package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType

enum class ImageCommentType(val value: String, val promiseUserType: PromiseUserType?) {
    NONE("NONE", null),

    // Can LATE
    RUNNING("달려가는 중️", PromiseUserType.LATE),
    GASPING("헐레벌떡", PromiseUserType.LATE),
    LEAVE_SOME("남겨놔!", PromiseUserType.LATE),
    WAIT_A_BIT("조금만 기다려", PromiseUserType.LATE),
    SORRY_LATE("늦어서 미안해", PromiseUserType.LATE),

    // Can WAIT
    WHAT_ARE_YOU_DOING("뭐해 심심해", PromiseUserType.WAIT),
    WHAT_TIME_IS_IT_NOW("지금이 몇시야!", PromiseUserType.WAIT),
    DID_YOU_COME("왔나..?", PromiseUserType.WAIT),
    I_LL_EAT_FIRST("먼저 먹을게~", PromiseUserType.WAIT),
    WHERE_ARE_YOU("너 어디야?", PromiseUserType.WAIT),
}
