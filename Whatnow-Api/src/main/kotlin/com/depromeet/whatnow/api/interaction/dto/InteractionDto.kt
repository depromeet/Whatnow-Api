package com.depromeet.whatnow.api.interaction.dto

import com.depromeet.whatnow.domains.interaction.domain.Interaction
import com.depromeet.whatnow.domains.interaction.domain.InteractionType

data class InteractionDto(
    val promiseId: Long,
    val userId: Long,
    val interactionType: InteractionType,
    val count: Long,
) {
    companion object {
        fun from(interaction: Interaction): InteractionDto {
            return InteractionDto(interaction.promiseId, interaction.userId, interaction.interactionType, interaction.count)
        }
    }
}
