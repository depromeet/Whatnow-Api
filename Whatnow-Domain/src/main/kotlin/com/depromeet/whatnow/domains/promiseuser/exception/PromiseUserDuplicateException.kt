package com.depromeet.whatnow.domains.promiseuser.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseUserDuplicateException : WhatnowCodeException(
    PromiseUserErrorCode.PROMISE_USER_DUPLICATE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseUserDuplicateException()
    }
}
