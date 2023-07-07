package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class UserProfileImageUpdatedEvent(
    val userId: Long,
    val imageKey: String,
) : DomainEvent()
