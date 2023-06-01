package com.depromeet.whatnow.domains.promiseuser.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.FORBIDDEN
import com.depromeet.whatnow.consts.NOT_FOUND
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class PromiseUserErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("이미 참여한 약속에 중복으로 참여한 경우 발생하는 오류")
    PROMISE_USER_ALREADY_SIGNUP(BAD_REQUEST, "PROMISE_USER_400_1", "이미 참여한 약속입니다."),

    @ExplainError("종료된 약속에 참여하려는 경우 발생하는 오류")
    PROMISE_USER_FORBIDDEN(FORBIDDEN, "PROMISE_USER_403_1", "이미 종료된 약속 입니다."),

    @ExplainError("약속유저 정보를 찾을 수 없는 경우")
    PROMISE_USER_NOT_FOUND(NOT_FOUND, "PROMISE_USER_404_1", "약속 유저 정보를 찾을 수 없습니다."),

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
