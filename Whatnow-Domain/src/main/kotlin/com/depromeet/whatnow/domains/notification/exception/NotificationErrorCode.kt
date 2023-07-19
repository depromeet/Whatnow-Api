package com.depromeet.whatnow.domains.notification.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class NotificationErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("알수 없는 알림 유형 일 경우")
    UNKNOWN_NOTIFICATION_TYPE(BAD_REQUEST, "NOTIFICATION_400_1", "알수 없는 알림 유형 입니다."),
    ;

    override val errorReason: ErrorReason
        get() {
            return ErrorReason(status, code, reason)
        }

    override val explainError: String
        get() {
            val field = this.javaClass.getField(name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return if (Objects.nonNull(annotation)) annotation.value else reason
        }
}
