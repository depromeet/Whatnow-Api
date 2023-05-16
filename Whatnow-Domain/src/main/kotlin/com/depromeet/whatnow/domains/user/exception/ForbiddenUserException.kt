package com.depromeet.whatnow.domains.user.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class ForbiddenUserException : WhatnowCodeException(
    UserErrorCode.USER_FORBIDDEN,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = ForbiddenUserException()
    }
}
