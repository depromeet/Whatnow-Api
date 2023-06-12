package com.depromeet.whatnow.domains.progresshistory.domain

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class PromiseProgress(
    val progressGroup: PromiseProgressGroup,
    val kr: String,
    val code: String,
) {
    SHOWER(PromiseProgressGroup.PREPARING, "씻는 중", "SHOWER"),
    LEAVE_SOON(PromiseProgressGroup.PREPARING, "곧 나가", "LEAVE_SOON"),
    BED(PromiseProgressGroup.PREPARING, "아직 침대", "BED"),
    WALKING(PromiseProgressGroup.MOVING, "걸어가는 중", "WALKING"),
    RUNNING(PromiseProgressGroup.MOVING, "뛰고 있어", "RUNNING"),
    BOARDING(PromiseProgressGroup.MOVING, "탑승 중", "BOARDING"),
    SORRY(PromiseProgressGroup.LATE, "미안해", "SORRY"),
    TEAR(PromiseProgressGroup.LATE, "눈물 줄줄", "TEAR"),
    URGENCY(PromiseProgressGroup.LATE, "급하다!!", "URGENCY"),
    AFTER_SOON(PromiseProgressGroup.EXPECTED_TIME, "곧 도착", "AFTER_SOON"),
    AFTER_5(PromiseProgressGroup.EXPECTED_TIME, "5분 뒤 도착", "AFTER_5"),
    AFTER_10(PromiseProgressGroup.EXPECTED_TIME, "10분 뒤 도착", "AFTER_10"),
    DEFAULT(PromiseProgressGroup.DEFAULT, "기본", "DEFAULT"),
}
