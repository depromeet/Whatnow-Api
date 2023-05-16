package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class ExpiredTokenException : WhatnowCodeException(
    GlobalErrorCode.TOKEN_EXPIRED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = ExpiredTokenException()
    }
}
