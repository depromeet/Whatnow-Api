package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class OtherServerExpiredTokenException : WhatnowCodeException(
    GlobalErrorCode.OTHER_SERVER_EXPIRED_TOKEN,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = OtherServerExpiredTokenException()
    }
}
