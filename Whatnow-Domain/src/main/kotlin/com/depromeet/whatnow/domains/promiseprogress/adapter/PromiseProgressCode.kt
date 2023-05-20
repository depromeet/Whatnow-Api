package com.depromeet.whatnow.domains.promiseprogress.adapter

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*
import com.depromeet.whatnow.consts.NOT_FOUND as NOT_FOUND_CONST

enum class PromiseProgressCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("약속 진행 단계를 찾을 수 없는 경우")
    NOT_FOUND(NOT_FOUND_CONST, "PROMISE_PROGRESS_404_1", "약속 진행 단계를 찾을 수 없습니다."),
    ;

    override val errorReason: ErrorReason
        get() { return ErrorReason(status, code, reason) }

    override val explainError: String
        get() {
            val field = this.javaClass.getField(name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return if (Objects.nonNull(annotation)) annotation.value else reason
        }
}
