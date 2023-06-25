package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.Objects

enum class ImageErrorCode(val status: Int, val code: String, val reason: String) : BaseErrorCode {
    @ExplainError("트래킹이 시작 전에 이미지를 업로드하는 경우")
    UPLOAD_BEFORE_TRACKING(BAD_REQUEST, "PICTURE_400_1", "트래킹 시작전에는 이미지를 업로드 할 수 없습니다."),

    @ExplainError("약속 참여에 취소한 유저가 업로드 하는 경우")
    CANCELLED_USER_UPLOAD(BAD_REQUEST, "PICTURE_400_2", "약속 취소한 유저는 이미지를 업로드 할 수 없습니다."),

    @ExplainError("유저의 상태와 일치하는 코멘트가 아닐 경우")
    INVALID_COMMENT_TYPE(BAD_REQUEST, "PICTURE_400_3", "유저의 상태와 일치하는 코멘트가 아닙니다."),
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