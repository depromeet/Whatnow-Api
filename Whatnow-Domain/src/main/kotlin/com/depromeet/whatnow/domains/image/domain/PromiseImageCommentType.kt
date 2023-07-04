package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.consts.ASSERT_IMAGE_DOMAIN
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class PromiseImageCommentType(
    val promiseUserType: PromiseUserType,
    val kr: String,
    val code: String,
    val image: String,
    val imageSmall: String,
) {
    // Can LATE
    RUNNING(PromiseUserType.LATE, "달려가는 중️", "RUNNING", "$ASSERT_IMAGE_DOMAIN/comments/RUNNING.svg", "$ASSERT_IMAGE_DOMAIN/comments/RUNNING_SMALL.svg"),
    GASPING(PromiseUserType.LATE, "헐레벌떡", "GASPING", "$ASSERT_IMAGE_DOMAIN/comments/GASPING.svg", "$ASSERT_IMAGE_DOMAIN/comments/GASPING_SMALL.svg"),
    LEAVE_SOME(PromiseUserType.LATE, "남겨놔!", "LEAVE_SOME", "$ASSERT_IMAGE_DOMAIN/comments/LEAVE_SOME.svg", "$ASSERT_IMAGE_DOMAIN/comments/LEAVE_SOME_SMALL.svg"),
    WAIT_A_BIT(PromiseUserType.LATE, "조금만 기다려", "WAIT_A_BIT", "$ASSERT_IMAGE_DOMAIN/comments/WAIT_A_BIT.svg", "$ASSERT_IMAGE_DOMAIN/comments/WAIT_A_BIT_SMALL.svg"),
    SORRY_LATE(PromiseUserType.LATE, "늦어서 미안해", "SORRY_LATE", "$ASSERT_IMAGE_DOMAIN/comments/SORRY_LATE.svg", "$ASSERT_IMAGE_DOMAIN/comments/SORRY_LATE_SMALL.svg"),

    // Can WAIT
    WHAT_ARE_YOU_DOING(PromiseUserType.WAIT, "뭐해 심심해", "WHAT_ARE_YOU_DOING", "$ASSERT_IMAGE_DOMAIN/comments/WHAT_ARE_YOU_DOING.svg", "$ASSERT_IMAGE_DOMAIN/comments/WHAT_ARE_YOU_DOING_SMALL.svg"),
    WHAT_TIME_IS_IT_NOW(PromiseUserType.WAIT, "지금이 몇시야!", "WHAT_TIME_IS_IT_NOW", "$ASSERT_IMAGE_DOMAIN/comments/WHAT_TIME_IS_IT_NOW.svg", "$ASSERT_IMAGE_DOMAIN/comments/WHAT_TIME_IS_IT_NOW_SMALL.svg"),
    DID_YOU_COME(PromiseUserType.WAIT, "왔나..?", "DID_YOU_COME", "$ASSERT_IMAGE_DOMAIN/comments/DID_YOU_COME.svg", "$ASSERT_IMAGE_DOMAIN/comments/DID_YOU_COME_SMALL.svg"),
    I_LL_EAT_FIRST(PromiseUserType.WAIT, "먼저 먹을게~", "I_LL_EAT_FIRST", "$ASSERT_IMAGE_DOMAIN/comments/I_LL_EAT_FIRST.svg", "$ASSERT_IMAGE_DOMAIN/comments/I_LL_EAT_FIRST_SMALL.svg"),
    WHERE_ARE_YOU(PromiseUserType.WAIT, "너 어디야?", "WHERE_ARE_YOU", "$ASSERT_IMAGE_DOMAIN/comments/WHERE_ARE_YOU.svg", "$ASSERT_IMAGE_DOMAIN/comments/WHERE_ARE_YOU_SMALL.svg"),
}
