package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class OtherServerForbiddenException : WhatnowCodeException(
    GlobalErrorCode.OTHER_SERVER_FORBIDDEN,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = OtherServerForbiddenException()
    }
}
