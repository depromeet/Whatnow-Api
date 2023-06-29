package com.depromeet.whatnow.domains.interactionhistory.repository

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interactionhistory.domain.InteractionHistory
import org.springframework.data.jpa.repository.JpaRepository

interface InteractionHistoryRepository : JpaRepository<InteractionHistory, Long> {
    fun findAllByUserIdAndPromiseIdAndInteractionType(userId: Long, promiseId: Long, interactionType: InteractionType): List<InteractionHistory>
}
