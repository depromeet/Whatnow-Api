package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

data class UserSignUpEvent(
    val userId: Long,
) : DomainEvent()
