package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.domains.progresshistory.exception.PromiseHistoryErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class InviteCodeNotFoundException : WhatnowCodeException(
    PromiseHistoryErrorCode.PROMISE_PROGRESS_IS_SAME,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InviteCodeNotFoundException()
    }
}
