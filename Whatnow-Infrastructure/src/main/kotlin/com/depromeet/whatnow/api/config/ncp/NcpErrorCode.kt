package com.depromeet.whatnow.api.config.ncp

import com.depromeet.whatnow.annotation.ExplainError
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.consts.FORBIDDEN
import com.depromeet.whatnow.consts.INTERNAL_SERVER
import com.depromeet.whatnow.consts.METHOD_NOT_ALLOWED
import com.depromeet.whatnow.consts.NOT_FOUND
import com.depromeet.whatnow.consts.UNAUTHORIZED
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.BaseErrorCode
import java.util.*

enum class NcpErrorCode(val status: Int, val errorCode: String, val error: String, val reason: String) : BaseErrorCode {
    NCP_001(
        BAD_REQUEST,
        "NCP_001",
        "misconfigured",
        "등록되지 않은 플랫폼에서 액세스 토큰을 요청 하는 경우",
    ),
    NCP400_1(
        BAD_REQUEST,
        "NCP_400_1",
        "invalid_request",
        "요청 변수 확인 : 필수 요청 변수가 빠졌거나 요청변수 이름이 잘못되었을 경우",
    ),
    NCP400_2(
        BAD_REQUEST,
        "NCP_401_1",
        "invalid_client",
        "애플리케이션 클라이언트 아이디와 시크릿 값이 없거나 잘못되었을 경우",
    ),
    NCP400_3(
        BAD_REQUEST,
        "NCP_400_3",
        "invalid_client",
        "클라이언트 아이디와 시크릿 값을 HTTP 헤더에 정확히 설정하지 않고 호출했을 경우",
    ),
    NCP400_4(
        BAD_REQUEST,
        "NCP_400_4",
        "invalid_client",
        "로그인 오픈 API를 호출할 때 접근 토큰(access_token) 값이 빠졌거나 잘못된 값(만료)을 설정하였을 경우",
    ),
    NCP401_1(
        UNAUTHORIZED,
        "NCP_401_3",
        "invalid_grant",
        "API 권한 설정이 안되어 있을 경우",
    ),
    NCP403_1(
        FORBIDDEN,
        "NCP_403_1",
        "invalid_request",
        "https가 아닌 http로 호출하였을 경우",
    ),
    NCP404_1(
        NOT_FOUND,
        "NCP_404_1",
        "not_found",
        "API 요청 URL이 잘못되었을 경우",
    ),
    NCP405_1(
        METHOD_NOT_ALLOWED,
        "NCP_405_1",
        "invalid_request",
        "HTTP 메서드를 잘못하여 호출하였을 경우 (POST인데 GET으로 호출)",
    ),
    NCP500_1(
        INTERNAL_SERVER,
        "NCP_500_1",
        "external_server_error",
        "API 호출은 정상적으로 했지만, API 서버 유지보수나 시스템 오류로 인한 에러가 발생하였을 경우",
    ),
    ;
    override val errorReason: ErrorReason
        get() { return ErrorReason(status, errorCode, reason) }

    override val explainError: String
        get() {
            val field = this.javaClass.getField(name)
            val annotation = field.getAnnotation(ExplainError::class.java)
            return if (Objects.nonNull(annotation)) annotation.value else reason
        }
}
