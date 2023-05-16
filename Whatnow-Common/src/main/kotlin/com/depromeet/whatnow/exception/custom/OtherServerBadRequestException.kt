package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class OtherServerBadRequestException : WhatnowCodeException(
    GlobalErrorCode.OTHER_SERVER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = OtherServerBadRequestException()
    }
}
