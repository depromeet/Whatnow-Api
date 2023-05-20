package com.depromeet.whatnow.domains.user.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UserNotFoundException : WhatnowCodeException(
    UserErrorCode.USER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UserNotFoundException()
    }
}
