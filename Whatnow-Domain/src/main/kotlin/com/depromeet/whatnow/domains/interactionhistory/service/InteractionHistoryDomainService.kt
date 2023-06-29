package com.depromeet.whatnow.domains.interactionhistory.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.common.aop.verify.CheckUserParticipation
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interactionhistory.adapter.InteractionHistoryAdapter
import com.depromeet.whatnow.domains.interactionhistory.domain.InteractionHistory

@DomainService
class InteractionHistoryDomainService(
    val interactionHistoryAdapter: InteractionHistoryAdapter,
) {
    fun sendInteraction(promiseId: Long, interactionType: InteractionType, userId: Long, targetUserId: Long) {
        interactionHistoryAdapter.save(InteractionHistory.of(promiseId, interactionType, userId, targetUserId))
    }

    @CheckUserParticipation
    fun queryAllByInteractionType(userId: Long, promiseId: Long, interactionType: InteractionType): List<InteractionHistory> {
        return interactionHistoryAdapter.queryAllByInteractionType(userId, promiseId, interactionType)
    }
}
