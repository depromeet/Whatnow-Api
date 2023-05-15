package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

interface BaseErrorCode {
    fun errorReason(): ErrorReason

//    @get:Throws(NoSuchFieldException::class)
    fun explainError(): String
}
