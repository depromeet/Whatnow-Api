package com.depromeet.whatnow.exception

import com.depromeet.whatnow.dto.ErrorReason
import org.springframework.beans.factory.annotation.Autowired

interface BaseErrorCode {
    val errorReason: ErrorReason?

    @get:Throws(NoSuchFieldException::class)
    val explainError: String?
}
