package com.depromeet.whatnow.domains.promiseuser.exception

import com.depromeet.whatnow.domains.user.exception.UserErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseUserNotFoundException : WhatnowCodeException(
    UserErrorCode.USER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseUserNotFoundException()
    }
}
