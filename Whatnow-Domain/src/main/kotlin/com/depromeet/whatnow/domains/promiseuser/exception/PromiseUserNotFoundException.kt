package com.depromeet.whatnow.domains.promiseuser.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseUserNotFoundException : WhatnowCodeException(
    PromiseUserErrorCode.PROMISE_USER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseUserNotFoundException()
    }
}
