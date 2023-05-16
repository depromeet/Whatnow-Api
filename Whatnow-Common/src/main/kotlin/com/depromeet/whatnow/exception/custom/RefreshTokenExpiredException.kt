package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class RefreshTokenExpiredException : WhatnowCodeException(
    GlobalErrorCode.REFRESH_TOKEN_EXPIRED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = RefreshTokenExpiredException()
    }
}
