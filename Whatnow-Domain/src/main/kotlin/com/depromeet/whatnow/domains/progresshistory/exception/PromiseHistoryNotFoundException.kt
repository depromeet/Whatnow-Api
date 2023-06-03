package com.depromeet.whatnow.domains.progresshistory.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseHistoryNotFoundException : WhatnowCodeException(
    PromiseHistoryErrorCode.PROMISE_HISTORY_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseHistoryNotFoundException()
    }
}
