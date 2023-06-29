package com.depromeet.whatnow.api.interaction.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.interaction.dto.InteractionDto
import com.depromeet.whatnow.api.interaction.dto.InteractionResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService

@UseCase
class InteractionReadUseCase(
    val interactionDomainService: InteractionDomainService,
) {

    fun findMyInteraction(promiseId: Long): InteractionResponse {
        val userId: Long = SecurityUtils.currentUserId
        return InteractionResponse(
            interactionDomainService.queryAllInteraction(promiseId, userId).map { InteractionDto.from(it) },
        )
    }
}
