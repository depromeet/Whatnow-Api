package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseNotFoundException : WhatnowCodeException(
    GlobalErrorCode.REFRESH_TOKEN_EXPIRED,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseNotFoundException()
    }
}
