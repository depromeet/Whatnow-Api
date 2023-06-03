package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter

@UseCase
class ProgressHistoryReadUseCase(
    val progressHistoryAdapter: ProgressHistoryAdapter,
) {
    fun execute(promiseId: Long, userId: Long): UserProgressResponse {
        progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
    }
}
