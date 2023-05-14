package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

interface BaseErrorCode {
    val errorReason: ErrorReason?

    @get:Throws(NoSuchFieldException::class)
    val explainError: String?
}
