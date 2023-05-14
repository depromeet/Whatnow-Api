package com.depromeet.whatnow.exception

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.FORBIDDEN
import com.depromeet.whatnow.consts.INTERNAL_SERVER
import com.depromeet.whatnow.consts.UNAUTHORIZED
import com.depromeet.whatnow.dto.ErrorReason
/**
 * 글로벌 관련 예외 코드들이 나온 곳입니다. 인증 , global, aop 종류등 도메인 제외한 exception 코드들이 모이는 곳입니다. 도메인 관련 Exception
 * code 들은 도메인 내부 exception 패키지에 위치시키면 됩니다.
 */
enum class GlobalErrorCode(status: Int, code: String, reason: String): BaseErrorCode {
    @ExplainError("백엔드에서 예시로만든 에러입니다. 개발용!이에유! 신경쓰지마세유")
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "GLOBAL_500_1", "서버 오류, 관리자에게 문의하세요");

    override val errorReason: ErrorReason
        get() = ErrorReason.builder()
            .status(status)
            .code(code)
            .reason(reason)
            .build()

    override val explainError: String
        get() = reason ?: ""


    var status: Int? = null
    var code: String? = null
    var reason: String? = null
}







