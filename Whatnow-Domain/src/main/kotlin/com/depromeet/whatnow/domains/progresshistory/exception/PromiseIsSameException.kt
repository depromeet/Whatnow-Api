package com.depromeet.whatnow.domains.progresshistory.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseIsSameException : WhatnowCodeException(
    PromiseHistoryErrorCode.PROMISE_PROGRESS_IS_SAME,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseIsSameException()
    }
}
