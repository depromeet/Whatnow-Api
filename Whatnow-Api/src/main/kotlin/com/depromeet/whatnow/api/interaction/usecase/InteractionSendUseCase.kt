package com.depromeet.whatnow.api.interaction.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interactionhistory.service.InteractionHistoryDomainService

@UseCase
class InteractionSendUseCase(
    val interactionHistoryDomainService: InteractionHistoryDomainService,
) {
    fun execute(promiseId: Long, interactionType: InteractionType, targetUserId: Long) {
        val userId = SecurityUtils.currentUserId
        interactionHistoryDomainService.sendInteraction(promiseId, interactionType, userId, targetUserId)
    }
}
