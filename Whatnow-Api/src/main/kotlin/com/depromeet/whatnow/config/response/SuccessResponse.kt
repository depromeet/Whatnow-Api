package com.depromeet.whatnow.config.response

import java.time.LocalDateTime

data class SuccessResponse(val status: Int, val data: Any?) {
    val success = true
    val timeStamp: LocalDateTime = LocalDateTime.now()
}
