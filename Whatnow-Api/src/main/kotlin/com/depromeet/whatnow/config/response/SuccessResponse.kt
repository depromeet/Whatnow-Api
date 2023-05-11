package com.depromeet.whatnow.config.response

import java.time.LocalDateTime

class SuccessResponse(private val status: Int, private val data: Any?) {
    private val success = true
    private val timeStamp: LocalDateTime = LocalDateTime.now()
}
