package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseRegisterEvent(
    val promiseId: Long,
    val userId: Long,
) : DomainEvent()
