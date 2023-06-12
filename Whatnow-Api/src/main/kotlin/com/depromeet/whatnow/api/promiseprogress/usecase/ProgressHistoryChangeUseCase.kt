package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressDto
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ProgressHistoryChangeUseCase(
    val promiseHistoryDomainService: ProgressHistoryDomainService,
    val userAdapter: UserAdapter,
    val promiseProgressAdapter: PromiseProgressAdapter,
) {
    fun execute(promiseId: Long, progressCode: String): UserProgressResponse {
        val userId = SecurityUtils.currentUserId
        val user = userAdapter.queryUser(userId)
        val newProgress = promiseProgressAdapter.queryByCode(progressCode)
        val history = promiseHistoryDomainService.change(promiseId, userId, newProgress.id!!)

        val preProgress = promiseProgressAdapter.queryId(history.prePromiseProgressId!!)
        return UserProgressResponse(user, PromiseProgressDto.from(newProgress), PromiseProgressDto.from(preProgress))
    }
}
