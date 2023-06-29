package com.depromeet.whatnow.domains.interaction.repository

import com.depromeet.whatnow.domains.interaction.domain.Interaction
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import org.springframework.data.jpa.repository.JpaRepository

interface InteractionRepository : JpaRepository<Interaction, Long> {
    fun findByPromiseIdAndUserIdAndInteractionType(
        promiseId: Long,
        userId: Long,
        interactionType: InteractionType,
    ): Interaction
}
