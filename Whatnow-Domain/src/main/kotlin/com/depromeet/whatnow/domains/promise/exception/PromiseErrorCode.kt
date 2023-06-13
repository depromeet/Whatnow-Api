package com.depromeet.whatnow.domains.promise.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.FORBIDDEN
import com.depromeet.whatnow.consts.NOT_FOUND
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class PromiseErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("이미 등록된 약속일 경우 발생하는 오류.")
    PROMISE_ALREADY_REGISTER(BAD_REQUEST, "PROMISE_400_1", "이미 회원가입한 약속입니다."),

    @ExplainError("정지 처리된 약속일 경우 발생하는 오류")
    PROMISE_FORBIDDEN(FORBIDDEN, "PROMISE_403_1", "접근이 제한된 약속입니다."),

    @ExplainError("방장이 아닌 참여자가 약속을 전체 취소할 경우")
    PROMISE_NOT_HOST(FORBIDDEN, "PROMISE_403_3", "방장이 아닌 참여자는 약속을 취소할 수 없습니다."),

    @ExplainError("삭제된 약속로 접근하려는 경우")
    PROMISE_ALREADY_DELETED(FORBIDDEN, "PROMISE_403_2", "이미 지워진 약속입니다."),

    @ExplainError("약속 정보를 찾을 수 없는 경우")
    PROMISE_NOT_FOUND(NOT_FOUND, "PROMISE_404_1", "약속 정보를 찾을 수 없습니다."),

    @ExplainError("이중약속을 잡은 경우")
    PROMISE_DOUBLE_PROMISE(BAD_REQUEST, "PROMISE_400_2", "이미 약속을 잡았습니다."),
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
