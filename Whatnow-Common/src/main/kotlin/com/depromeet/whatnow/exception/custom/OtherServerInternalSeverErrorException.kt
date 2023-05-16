package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class OtherServerInternalSeverErrorException : WhatnowCodeException(
    GlobalErrorCode.OTHER_SERVER_INTERNAL_SERVER_ERROR,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = OtherServerInternalSeverErrorException()
    }
}
