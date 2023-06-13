package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import java.time.LocalDateTime

class PromiseUpdateEndTimeEvent(
    val promiseId: Long,
    val endTime: LocalDateTime,
) : DomainEvent()
