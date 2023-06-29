package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class NotParticipatedInPromiseException : WhatnowCodeException(
    GlobalErrorCode.USER_NOT_PARTICIPATE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = NotParticipatedInPromiseException()
    }
}
