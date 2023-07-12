package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

data class UserRegisterEvent(
    val userId: Long,
) : DomainEvent()
