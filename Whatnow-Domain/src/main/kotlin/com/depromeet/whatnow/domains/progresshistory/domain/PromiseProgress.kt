package com.depromeet.whatnow.domains.progresshistory.domain

import com.depromeet.whatnow.consts.ASSERT_IMAGE_DOMAIN
import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class PromiseProgress(
    val progressGroup: PromiseProgressGroup,
    val kr: String,
    val code: String,
    val image: String,
) {
    SHOWER(PromiseProgressGroup.PREPARING, "씻는 중", "SHOWER", "$ASSERT_IMAGE_DOMAIN/progresses/SHOWER.svg"),
    LEAVE_SOON(PromiseProgressGroup.PREPARING, "곧 나가", "LEAVE_SOON", "$ASSERT_IMAGE_DOMAIN/progresses/LEAVE_SOON.svg"),
    BED(PromiseProgressGroup.PREPARING, "아직 침대", "BED", "$ASSERT_IMAGE_DOMAIN/progresses/BED.svg"),
    WALKING(PromiseProgressGroup.MOVING, "걸어가는 중", "WALKING", "$ASSERT_IMAGE_DOMAIN/progresses/WALKING.svg"),
    RUNNING(PromiseProgressGroup.MOVING, "뛰고 있어", "RUNNING", "$ASSERT_IMAGE_DOMAIN/progresses/RUNNING.svg"),
    BOARDING(PromiseProgressGroup.MOVING, "탑승 중", "BOARDING", "$ASSERT_IMAGE_DOMAIN/progresses/BOARDING.svg"),
    SORRY(PromiseProgressGroup.LATE, "미안해", "SORRY", "$ASSERT_IMAGE_DOMAIN/progresses/SORRY.svg"),
    TEAR(PromiseProgressGroup.LATE, "눈물 줄줄", "TEAR", "$ASSERT_IMAGE_DOMAIN/progresses/TEAR.svg"),
    URGENCY(PromiseProgressGroup.LATE, "급하다!!", "URGENCY", "$ASSERT_IMAGE_DOMAIN/progresses/URGENCY.svg"),
    AFTER_SOON(PromiseProgressGroup.EXPECTED_TIME, "곧 도착", "AFTER_SOON", "$ASSERT_IMAGE_DOMAIN/progresses/AFTER_SOON.svg"),
    AFTER_5(PromiseProgressGroup.EXPECTED_TIME, "5분 뒤 도착", "AFTER_5", "$ASSERT_IMAGE_DOMAIN/progresses/AFTER_5.svg"),
    AFTER_10(PromiseProgressGroup.EXPECTED_TIME, "10분 뒤 도착", "AFTER_10", "$ASSERT_IMAGE_DOMAIN/progresses/AFTER_10.svg"),
    DEFAULT(PromiseProgressGroup.DEFAULT, "기본", "DEFAULT", "$ASSERT_IMAGE_DOMAIN/progresses/DEFAULT.svg"),
}
