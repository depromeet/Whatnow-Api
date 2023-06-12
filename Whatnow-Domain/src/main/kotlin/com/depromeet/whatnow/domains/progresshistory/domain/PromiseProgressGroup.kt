package com.depromeet.whatnow.domains.progresshistory.domain

enum class PromiseProgressGroup(val kr: String) {
    PREPARING("출발 전"),
    MOVING("가는 중"),
    LATE("지각"),
    EXPECTED_TIME("예정시간"),
    DEFAULT("기본"),
}
