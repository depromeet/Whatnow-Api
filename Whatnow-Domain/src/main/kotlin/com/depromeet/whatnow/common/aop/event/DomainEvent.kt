package com.depromeet.whatnow.common.aop.event

import java.time.LocalDateTime

open class DomainEvent {
    val publishAt: LocalDateTime = LocalDateTime.now()
}
