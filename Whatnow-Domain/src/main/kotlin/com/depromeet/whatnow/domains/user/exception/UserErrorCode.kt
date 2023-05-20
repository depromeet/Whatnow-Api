package com.depromeet.whatnow.domains.user.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.FORBIDDEN
import com.depromeet.whatnow.consts.NOT_FOUND
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class UserErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {

    @ExplainError("회원가입시에 이미 회원가입한 유저일시 발생하는 오류. 회원가입전엔 항상 register valid check 를 해주세요")
    USER_ALREADY_SIGNUP(BAD_REQUEST, "USER_400_1", "이미 회원가입한 유저입니다."),

    @ExplainError("정지 처리된 유저일 경우 발생하는 오류")
    USER_FORBIDDEN(FORBIDDEN, "USER_403_1", "접근이 제한된 유저입니다."),

    @ExplainError("탈퇴한 유저로 접근하려는 경우")
    USER_ALREADY_DELETED(FORBIDDEN, "USER_403_2", "이미 지워진 유저입니다."),

    @ExplainError("유저 정보를 찾을 수 없는 경우")
    USER_NOT_FOUND(NOT_FOUND, "USER_404_1", "유저 정보를 찾을 수 없습니다."),

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
