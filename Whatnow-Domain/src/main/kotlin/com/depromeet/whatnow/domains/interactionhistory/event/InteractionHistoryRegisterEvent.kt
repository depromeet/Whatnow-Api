package com.depromeet.whatnow.domains.interactionhistory.event

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.domains.interaction.domain.InteractionType

class InteractionHistoryRegisterEvent(
    val promiseId: Long,
    val interactionType: InteractionType,
    val userId: Long,
    val targetUserId: Long,
) : DomainEvent()
