package com.depromeet.whatnow.domains.user.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class AlreadyDeletedUserException : WhatnowCodeException(
    UserErrorCode.USER_ALREADY_DELETED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = AlreadyDeletedUserException()
    }
}
