package com.depromeet.whatnow.dto

data class ErrorReason(
    var status: Int,
    var code: String,
    var reason: String,
) {

    companion object {
        fun of(status: Int, code: String, reason: String): ErrorReason {
            return ErrorReason(
                status = status,
                code = code,
                reason = reason,
            )
        }
    }
}
