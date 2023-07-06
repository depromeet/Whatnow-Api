package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseNotParticipateException : WhatnowCodeException(
    GlobalErrorCode.USER_NOT_PARTICIPATE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseNotParticipateException()
    }
}
