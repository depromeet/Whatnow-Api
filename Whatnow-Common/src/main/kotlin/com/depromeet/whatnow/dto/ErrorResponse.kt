package com.depromeet.whatnow.dto

import java.time.LocalDateTime

data class ErrorResponse(
    val status: Int,
    val code: String,
    val reason: String?,
    val path: String,
) {

    val timeStamp: LocalDateTime = LocalDateTime.now()

    companion object {
        fun of(errorReason: ErrorReason, path: String): ErrorResponse {
            return ErrorResponse(errorReason.status, errorReason.code, errorReason.reason, path = path)
        }
    }
}
