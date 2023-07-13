package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseUserUpdateLocationEvent(
    val promiseId: Long,
    val userId: Long,
    val id: Long,
) : DomainEvent()
