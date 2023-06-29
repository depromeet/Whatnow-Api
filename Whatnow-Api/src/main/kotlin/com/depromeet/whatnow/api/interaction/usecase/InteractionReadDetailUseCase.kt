package com.depromeet.whatnow.api.interaction.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.interaction.dto.InteractionDetailDto
import com.depromeet.whatnow.api.interaction.dto.InteractionDetailResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.interactionhistory.service.InteractionHistoryDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class InteractionReadDetailUseCase(
    val interactionHistoryDomainService: InteractionHistoryDomainService,
    val userAdapter: UserAdapter,
) {
    fun findMyInteractionDetail(promiseId: Long, interactionType: InteractionType): InteractionDetailResponse {
        val userId = SecurityUtils.currentUserId
        return InteractionDetailResponse(
            interactionHistoryDomainService.queryAllByInteractionType(userId, promiseId, interactionType)
                .groupBy { it.targetUserId }.map { (targetUserId, interactionHistories) ->
                    InteractionDetailDto(
                        userAdapter.queryUser(targetUserId).toUserInfoVo(),
                        interactionHistories.size.toLong(),
                        interactionType,
                    )
                }.sortedByDescending { it.count },
        )
    }
}
