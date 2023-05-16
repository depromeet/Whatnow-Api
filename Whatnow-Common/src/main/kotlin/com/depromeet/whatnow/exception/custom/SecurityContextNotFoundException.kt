package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class SecurityContextNotFoundException : WhatnowCodeException(
    GlobalErrorCode.SECURITY_CONTEXT_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = SecurityContextNotFoundException()
    }
}
