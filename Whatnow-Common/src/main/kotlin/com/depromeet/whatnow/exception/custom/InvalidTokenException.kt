package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class InvalidTokenException : WhatnowCodeException(
    GlobalErrorCode.INVALID_TOKEN,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InvalidTokenException()
    }
}
