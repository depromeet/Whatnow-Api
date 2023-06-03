package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService

@UseCase
class ProgressHistoryChangeUseCase(
    val promiseHistoryDomainService: ProgressHistoryDomainService,
) {
    fun execute(promiseId: Long, progressCode: Long): UserProgressResponse {
        promiseHistoryDomainService.change(promiseId, progressCode)
    }
}
