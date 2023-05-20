package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason

interface BaseErrorCode {
    val errorReason: ErrorReason

    val explainError: String
}
