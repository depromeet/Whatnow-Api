package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressDto
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.promiseprogress.adapter.PromiseProgressAdapter
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ProgressHistoryReadUseCase(
    val progressHistoryAdapter: ProgressHistoryAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val promiseProgressAdapter: PromiseProgressAdapter,
    val userAdapter: UserAdapter,
) {
    fun execute(promiseId: Long, userId: Long): UserProgressResponse {
        promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId) // 해당 유저가 있는지 검증을... 함 해야햐긴함!

        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        val preProgress = promiseProgressAdapter.queryId(history.prePromiseProgressId!!)
        val newProgress = promiseProgressAdapter.queryId(history.afterPromiseProgressId!!)
        return UserProgressResponse(userAdapter.queryUser(userId), PromiseProgressDto.from(newProgress), PromiseProgressDto.from(preProgress))
    }
}
