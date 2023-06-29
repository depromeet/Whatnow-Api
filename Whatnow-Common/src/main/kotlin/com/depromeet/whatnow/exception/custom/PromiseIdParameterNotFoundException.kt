package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseIdParameterNotFoundException: WhatnowCodeException(
    GlobalErrorCode.PROMISE_ID_PARAMETER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseIdParameterNotFoundException()
    }
}