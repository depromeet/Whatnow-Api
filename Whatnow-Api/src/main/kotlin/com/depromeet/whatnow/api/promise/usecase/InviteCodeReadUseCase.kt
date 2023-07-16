package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.domains.invitecode.adapter.InviteCodeAdapter

@UseCase
class InviteCodeReadUseCase(
    val inviteCodeAdapter: InviteCodeAdapter,
) {
    fun findInviteCodeByPromiseId(promiseId: Long): String {
        return inviteCodeAdapter.findByPromiseId(promiseId).inviteCode
    }
}
