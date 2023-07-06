package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseNotActivateException : WhatnowCodeException(
    PromiseErrorCode.PROMISE_NOT_ACTIVATE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseNotFoundException()
    }
}
