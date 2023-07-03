package com.depromeet.whatnow.api.interaction.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.interaction.dto.InteractionDto
import com.depromeet.whatnow.api.interaction.dto.InteractionResponse
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class InteractionReadUseCase(
    val interactionDomainService: InteractionDomainService,
    val progressHistoryAdapter: ProgressHistoryAdapter,
    val userAdapter: UserAdapter,
) {

    fun findMyInteraction(promiseId: Long): InteractionResponse {
        val userId: Long = SecurityUtils.currentUserId
        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        val userProgressResponse = UserProgressResponse(
            userAdapter.queryUser(userId).toUserInfoVo(),
            history.currentPromiseProgress,
            history.prePromiseProgress,
        )
        return InteractionResponse(
            userProgressResponse,
            interactionDomainService.queryAllInteraction(promiseId, userId).map { InteractionDto.from(it) },
        )
    }
}
