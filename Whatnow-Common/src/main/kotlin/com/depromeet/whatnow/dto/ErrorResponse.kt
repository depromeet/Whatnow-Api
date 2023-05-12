package com.depromeet.whatnow.dto

import java.time.LocalDateTime

class ErrorResponse(
    var status: Int? = null,
    var code: String? = null,
    var reason: String? = null,
    var timeStamp: LocalDateTime? = null,
    var path: String? = null,
) {
    constructor(errorReason: ErrorReason?, path: String) : this() {
        status = errorReason?.status
        code = errorReason?.code
        reason = errorReason?.reason
        timeStamp = LocalDateTime.now()
        this.path = path
    }
}
