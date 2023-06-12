package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.user.adapter.UserAdapter

@UseCase
class ProgressHistoryReadUseCase(
    val progressHistoryAdapter: ProgressHistoryAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
) {
    fun execute(promiseId: Long, userId: Long): UserProgressResponse {
        // 해당 유저가 해당 약속에 있는지 검증을... 함 해야햐긴함!
        // 이부분은 나중에 깔쌈하게 누가 aop로 만들면 참 좋을듯
        promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        return UserProgressResponse(userAdapter.queryUser(userId), history.currentPromiseProgress, history.prePromiseProgress)
    }
}
