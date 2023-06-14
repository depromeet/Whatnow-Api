package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ProgressHistoryChangeUseCase(
    val promiseHistoryDomainService: ProgressHistoryDomainService,
    val userAdapter: UserAdapter,
) {
    fun execute(promiseId: Long, progress: PromiseProgress): UserProgressResponse {
        val userId = SecurityUtils.currentUserId
        val user = userAdapter.queryUser(userId)
        val currentHistory = promiseHistoryDomainService.change(promiseId, userId, progress)
        return UserProgressResponse(user.toUserInfoVo(), currentHistory.currentPromiseProgress, currentHistory.prePromiseProgress)
    }
}
