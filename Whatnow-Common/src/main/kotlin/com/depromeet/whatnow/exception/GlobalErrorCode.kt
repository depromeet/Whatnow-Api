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
    INTERNAL_SERVER_ERROR(INTERNAL_SERVER, "GLOBAL_500_1", "서버 오류, 관리자에게 문의하세요"),

    @ExplainError("밸리데이션 (검증 과정 수행속 ) 발생하는 오류입니다.")
    ARGUMENT_NOT_VALID_ERROR(BAD_REQUEST,"GLOBAL_400_1", "잘못된 요청입니다."),

    @ExplainError("accessToken 만료시 발생하는 오류입니다.")
    TOKEN_EXPIRED(UNAUTHORIZED, "AUTH_401_1", "토큰이 만료되었습니다."),

    @ExplainError("refresgToken 만료시 발생하는 오류입니다.")
    REFRESH_TOKEN_EXPIRED(FORBIDDEN,"AUTH_403_1", "인증시간이 만료되었습니다. 인증토큰 재발급을 요청하세요"),

    @ExplainError("헤더에 accessToken의 형식이 틀릴떄 발생하는 오류입니다, 토큰이 위조됐을 가능성이 있습니다.")
    ACCESS_TOKEN_FORMAT_ERROR(UNAUTHORIZED, "AUTH_403_2", "토큰의 형식이 일치하지 않습니다. 적절한 형식으로 다시 요청하세요"),

    @ExplainError("헤더에 accessToken이 없을때 발생하는 오류입니다")
    ACCESS_TOKEN_NOT_FOUND(UNAUTHORIZED, "AUTH_403_3", "토큰이 존재하지 않습니다. 적절한 토큰을 헤더에 넣어주세요"),




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







