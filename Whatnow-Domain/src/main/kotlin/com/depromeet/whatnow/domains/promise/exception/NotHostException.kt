package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class NotHostException : WhatnowCodeException(
    PromiseErrorCode.PROMISE_NOT_HOST,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = NotHostException()
    }
}
