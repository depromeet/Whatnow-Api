package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class OtherServerUnauthorizedException : WhatnowCodeException(
    GlobalErrorCode.OTHER_SERVER_UNAUTHORIZED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = OtherServerUnauthorizedException()
    }
}
