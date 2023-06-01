package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

data class PromiseUserRegisterEvent(
    val promiseId: Long,
    val userId: Long,
    val promiseUserId: Long,
) : DomainEvent()
