package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

class CodeException(
    e:ErrorReason
) : RuntimeException() {

    var errorCode: BaseErrorCode? = null
    fun getErrorReason(): ErrorReason? {
        return errorCode?.errorReason
    }
}
