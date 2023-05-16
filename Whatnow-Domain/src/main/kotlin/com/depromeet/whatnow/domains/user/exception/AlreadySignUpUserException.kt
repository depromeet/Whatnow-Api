package com.depromeet.whatnow.domains.user.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class AlreadySignUpUserException : WhatnowCodeException(
    UserErrorCode.USER_ALREADY_SIGNUP,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = AlreadySignUpUserException()
    }
}
