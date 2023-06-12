package com.depromeet.whatnow.domains.progresshistory.domain

enum class PromiseProgress(
    val progressGroup: PromiseProgressGroup,
    val kr: String,
) {
    SHOWER(PromiseProgressGroup.PREPARING, "씻는 중"),
    LEAVE_SOON(PromiseProgressGroup.PREPARING, "곧 나가"),
    BED(PromiseProgressGroup.PREPARING, "아직 침대"),
    WALKING(PromiseProgressGroup.MOVING, "걸어가는 중"),
    RUNNING(PromiseProgressGroup.MOVING, "뛰고 있어"),
    BOARDING(PromiseProgressGroup.MOVING, "탑승 중"),
    SORRY(PromiseProgressGroup.LATE, "미안해"),
    TEAR(PromiseProgressGroup.LATE, "눈물 줄줄"),
    URGENCY(PromiseProgressGroup.LATE, "급하다!!"),
    AFTER_SOON(PromiseProgressGroup.EXPECTED_TIME, "곧 도착"),
    AFTER_5(PromiseProgressGroup.EXPECTED_TIME, "5분 뒤 도착"),
    AFTER_10(PromiseProgressGroup.EXPECTED_TIME, "10분 뒤 도착"),
    DEFAULT(PromiseProgressGroup.DEFAULT, "기본"),
}