package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

class CodeException : RuntimeException() {
    var errorCode: BaseErrorCode? = null
    fun getErrorReason(): ErrorReason? {
        return errorCode?.errorReason
    }
}
