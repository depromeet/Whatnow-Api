package com.depromeet.whatnow.common.aop.event

import java.time.LocalDateTime

class DomainEvent {
    val publishAt: LocalDateTime = LocalDateTime.now()
}
