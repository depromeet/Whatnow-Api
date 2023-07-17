package com.depromeet.whatnow.domains.invitecode.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.Objects

enum class InviteCodeErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {
    @ExplainError("약속에 중복된 초대코드를 생성한 경우")
    INVITE_CODE_DUPLICATE_CREATED(BAD_REQUEST, "INVITE_CODE_400_1", "중복된 초대코르를 생성하셨습니다."),

    @ExplainError("조회한 초대코드가 존재하지 않는 경우")
    INVITE_CODE_NOT_FOUND(BAD_REQUEST, "INVITE_CODE_400_2", "조회하신 초대코드가 조회되지 않습니다. 다시 시도하여 주십시오."),

    @ExplainError("조회한 초대코드의 형식이 일치하지 않는 경우")
    INVITE_CODE_FORMAT_MISMATCH(BAD_REQUEST, "INVITE_CODE_400_3", "조회한 초대 코드의 형식이 일치하지 않습니다."),

    @ExplainError("조회한 초대코드가 만료된 경우")
    INVITE_CODE_EXPIRED(BAD_REQUEST, "INVITE_CODE_400_4", "초대코드가 만료되었습니다, 다시 생성하여 주세요."),
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
