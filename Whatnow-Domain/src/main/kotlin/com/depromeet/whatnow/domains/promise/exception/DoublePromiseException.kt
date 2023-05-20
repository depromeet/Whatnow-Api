package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class DoublePromiseException : WhatnowCodeException(
    PromiseErrorCode.PROMISE_DOUBLE_PROMISE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = DoublePromiseException()
    }
}
