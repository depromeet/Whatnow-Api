package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.domains.interaction.domain.InteractionType

class InteractionFixedEvent(
    val promiseId: Long,
    val userId: Long,
    val interactionType: InteractionType,
) : DomainEvent()
