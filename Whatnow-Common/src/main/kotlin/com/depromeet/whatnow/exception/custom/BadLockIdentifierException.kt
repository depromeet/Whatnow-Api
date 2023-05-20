package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class BadLockIdentifierException : WhatnowCodeException(
    GlobalErrorCode.BAD_LOCK_IDENTIFIER,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = BadLockIdentifierException()
    }
}
