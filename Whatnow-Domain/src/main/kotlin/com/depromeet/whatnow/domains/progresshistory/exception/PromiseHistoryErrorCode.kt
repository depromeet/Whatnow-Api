package com.depromeet.whatnow.domains.progresshistory.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.NOT_FOUND
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class PromiseHistoryErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("약속 히스토리 정보를 찾을 수 없는 경우")
    PROMISE_HISTORY_NOT_FOUND(NOT_FOUND, "PROMISE_HISTORY_404_1", "약속 히스토리 정보를 찾을 수 없습니다."),

    @ExplainError("변경하려는 약속 단계가 동일할 경우")
    PROMISE_PROGRESS_IS_SAME(BAD_REQUEST, "PROMISE_HISTORY_400_1", "변경하려는 약속 단계가 동일합니다."),

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
