package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

class CodeException(
    private val errorCode: BaseErrorCode,
) : RuntimeException() {

    val errorReason : ErrorReason
        get() {
            return errorCode.errorReason
        }
}
