package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseNotFoundException : WhatnowCodeException(
    PromiseErrorCode.PROMISE_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseNotFoundException()
    }
}
