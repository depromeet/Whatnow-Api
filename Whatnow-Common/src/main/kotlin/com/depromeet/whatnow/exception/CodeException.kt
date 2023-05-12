package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

class CodeException : RuntimeException() {
    private val errorCode: BaseErrorCode? = null
    fun getErrorReason(): ErrorReason? {
        return errorCode?.errorReason
    }
}
