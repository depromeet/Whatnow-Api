package com.depromeet.whatnow.domains.notification.domain

enum class NotificationType(val kr: String) {
    MEET("만났다"),
    ARRIVAL("도착"),
    TIMEOVER("시간 초과"),
    INTERACTION("인터렉션"),
    INTERACTION_ATTAINMENT("인터렉션 달성"),
    IMAGE("이미지"),
    START_SHARING("공유 시작"),
    END_SHARING("공유 종료"),
}
