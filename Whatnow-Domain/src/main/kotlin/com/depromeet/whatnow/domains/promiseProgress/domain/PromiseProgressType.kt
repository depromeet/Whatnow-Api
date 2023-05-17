package com.depromeet.whatnow.domains.promiseProgress.domain

enum class PromiseProgressType(val kr: String) {
    PREPARING("출발 전"),
    MOVING("이동 중"),
    ARRIVED("도착"),
    ETC("기타"),
}
