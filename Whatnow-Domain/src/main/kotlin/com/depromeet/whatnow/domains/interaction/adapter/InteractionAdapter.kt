package com.depromeet.whatnow.domains.interaction.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.interaction.domain.Interaction
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interaction.repository.InteractionRepository

@Adapter
class InteractionAdapter(
    val interactionRepository: InteractionRepository,
) {
    fun save(interaction: Interaction) {
        interactionRepository.save(interaction)
    }

    fun queryInteraction(promiseId: Long, userId: Long, interactionType: InteractionType): Interaction {
        return interactionRepository.findByPromiseIdAndUserIdAndInteractionType(promiseId, userId, interactionType)
    }

    fun queryAllInteraction(promiseId: Long, userId: Long): List<Interaction> {
        return interactionRepository.findAllByPromiseIdAndUserId(promiseId, userId)
    }
}
