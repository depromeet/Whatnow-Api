package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseUserCancelEvent(
    val promiseId: Long,
    val userId: Long,
    val promiseUserId: Long,
) : DomainEvent()
