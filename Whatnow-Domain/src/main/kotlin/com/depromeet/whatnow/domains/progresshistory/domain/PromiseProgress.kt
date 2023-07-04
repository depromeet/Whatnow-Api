package com.depromeet.whatnow.domains.progresshistory.domain

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class PromiseProgress(
    val progressGroup: PromiseProgressGroup,
    val kr: String,
    val code: String,
    val image: String,
) {
    SHOWER(PromiseProgressGroup.PREPARING, "씻는 중", "SHOWER", "https://image.whatnow.kr/assert/progresses/SHOWER.svg"),
    LEAVE_SOON(PromiseProgressGroup.PREPARING, "곧 나가", "LEAVE_SOON", "https://image.whatnow.kr/assert/progresses/LEAVE_SOON.svg"),
    BED(PromiseProgressGroup.PREPARING, "아직 침대", "BED", "https://image.whatnow.kr/assert/progresses/BED.svg"),
    WALKING(PromiseProgressGroup.MOVING, "걸어가는 중", "WALKING", "https://image.whatnow.kr/assert/progresses/WALKING.svg"),
    RUNNING(PromiseProgressGroup.MOVING, "뛰고 있어", "RUNNING", "https://image.whatnow.kr/assert/progresses/RUNNING.svg"),
    BOARDING(PromiseProgressGroup.MOVING, "탑승 중", "BOARDING", "https://image.whatnow.kr/assert/progresses/BOARDING.svg"),
    SORRY(PromiseProgressGroup.LATE, "미안해", "SORRY", "https://image.whatnow.kr/assert/progresses/SORRY.svg"),
    TEAR(PromiseProgressGroup.LATE, "눈물 줄줄", "TEAR", "https://image.whatnow.kr/assert/progresses/TEAR.svg"),
    URGENCY(PromiseProgressGroup.LATE, "급하다!!", "URGENCY", "https://image.whatnow.kr/assert/progresses/URGENCY.svg"),
    AFTER_SOON(PromiseProgressGroup.EXPECTED_TIME, "곧 도착", "AFTER_SOON", "https://image.whatnow.kr/assert/progresses/AFTER_SOON.svg"),
    AFTER_5(PromiseProgressGroup.EXPECTED_TIME, "5분 뒤 도착", "AFTER_5", "https://image.whatnow.kr/assert/progresses/AFTER_5.svg"),
    AFTER_10(PromiseProgressGroup.EXPECTED_TIME, "10분 뒤 도착", "AFTER_10", "https://image.whatnow.kr/assert/progresses/AFTER_10.svg"),
    DEFAULT(PromiseProgressGroup.DEFAULT, "기본", "DEFAULT", "https://image.whatnow.kr/assert/progresses/DEFAULT.svg"),
}
