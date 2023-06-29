package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.common.aop.verify.CheckUserParticipation
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ProgressHistoryReadUseCase(
    val progressHistoryAdapter: ProgressHistoryAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
) {
    @CheckUserParticipation
    fun execute(promiseId: Long, userId: Long): UserProgressResponse {
        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        return UserProgressResponse(userAdapter.queryUser(userId).toUserInfoVo(), history.currentPromiseProgress, history.prePromiseProgress)
    }
}
