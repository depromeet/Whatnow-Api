package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseIdConversionException: WhatnowCodeException(
    GlobalErrorCode.PROMISE_ID_NOT_LONG,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseIdConversionException()
    }
}