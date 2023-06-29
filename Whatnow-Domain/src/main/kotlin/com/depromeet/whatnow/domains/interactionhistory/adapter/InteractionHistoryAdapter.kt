package com.depromeet.whatnow.domains.interactionhistory.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interactionhistory.domain.InteractionHistory
import com.depromeet.whatnow.domains.interactionhistory.repository.InteractionHistoryRepository

@Adapter
class InteractionHistoryAdapter(
    val interactionHistoryRepository: InteractionHistoryRepository,
) {
    fun save(interactionHistory: InteractionHistory) {
        interactionHistoryRepository.save(interactionHistory)
    }

    fun queryAllByInteractionType(userId: Long, promiseId: Long, interactionType: InteractionType): List<InteractionHistory> {
        return interactionHistoryRepository.findAllByUserIdAndPromiseIdAndInteractionType(userId, promiseId, interactionType)
    }
}
