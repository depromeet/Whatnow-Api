package com.depromeet.whatnow.config.rateLimit

import band.gosrock.api.config.rateLimit.IPRateLimiter
import com.depromeet.whatnow.api.config.slack.SlackAsyncErrorSender
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.dto.ErrorResponse
import com.depromeet.whatnow.exception.GlobalErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.bucket4j.Bucket
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.ContentCachingRequestWrapper
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ThrottlingInterceptor(
    val userRateLimiter: UserRateLimiter? = null,
    val ipRateLimiter: IPRateLimiter? = null,
    val objectMapper: ObjectMapper? = null,

    @Value("\${acl.whiteList}")
    private val aclWhiteList: List<String>? = null,

    private val slackThrottleErrorSender: SlackAsyncErrorSender? = null,
) : HandlerInterceptor {

    // logger
    private val log = LoggerFactory.getLogger(ThrottlingInterceptor::class.java)

    @Throws(IOException::class)
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val userId: Long = SecurityUtils.currentUserId
        val remoteAddr = request.remoteAddr
        log.info("remoteAddr : $remoteAddr")

        // next js ssr 대응
        if (aclWhiteList!!.contains(remoteAddr)) {
            log.info("white List pass$remoteAddr")
            return true
        }
        val bucket: Bucket
        bucket = if (userId == 0L) {
            // 익명 유저 ip 기반처리
            ipRateLimiter!!.resolveBucket(remoteAddr)
        } else {
            // 비 익명 유저 유저 아이디 기반 처리
            userRateLimiter!!.resolveBucket(userId.toString())
        }
        val availableTokens = bucket.availableTokens
        log.info("$userId : $availableTokens")
        if (bucket.tryConsume(1)) {
            return true
        }

        // 허용량 초과시 슬랙 알림 메시지 발송.
        val cachingRequest = request as ContentCachingRequestWrapper
        slackThrottleErrorSender?.execute(cachingRequest, userId)
        responseTooManyRequestError(request, response)
        return false
    }

    @Throws(IOException::class)
    private fun responseTooManyRequestError(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val errorResponse = ErrorResponse(
            GlobalErrorCode.SERVER_TOO_MANY_REQUESTS.errorReason.status,
            GlobalErrorCode.SERVER_TOO_MANY_REQUESTS.errorReason.code,
            GlobalErrorCode.SERVER_TOO_MANY_REQUESTS.errorReason.reason,
            request.requestURL.toString(),
        )
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = errorResponse.status
        response.writer.write(objectMapper!!.writeValueAsString(errorResponse))
    }
}
