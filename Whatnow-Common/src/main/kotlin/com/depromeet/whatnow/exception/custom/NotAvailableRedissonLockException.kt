package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class NotAvailableRedissonLockException : WhatnowCodeException(
    GlobalErrorCode.NOT_AVAILABLE_REDISSON_LOCK,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = NotAvailableRedissonLockException()
    }
}
