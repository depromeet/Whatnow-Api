package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseCancelEvent(
    id: Long,
) : DomainEvent()
