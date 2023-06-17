package com.depromeet.whatnow.config.response

import com.depromeet.whatnow.api.config.slack.SlackAsyncErrorSender
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.dto.ErrorResponse
import com.depromeet.whatnow.exception.GlobalErrorCode.INTERNAL_SERVER_ERROR
import com.depromeet.whatnow.exception.WhatNowDynamicException
import com.depromeet.whatnow.exception.WhatnowCodeException
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    @Autowired
    lateinit var slackProvider: SlackAsyncErrorSender

    /**요청 url을 resource로 담아 상위 객체에 처리를 위임한다.*/
    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val servletWebRequest = request as ServletWebRequest
        val url = UriComponentsBuilder.fromHttpRequest(
            ServletServerHttpRequest(servletWebRequest.request),
        )
            .build()
            .toUriString()

        val errorResponse = ErrorResponse(
            status.value(),
            status.name,
            ex.message,
            url,
        )
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val url = UriComponentsBuilder.fromHttpRequest(
            ServletServerHttpRequest((request as ServletWebRequest).request),
        )
            .build()
            .toUriString()
        val fieldErrorMessage = ex.bindingResult.fieldErrors
            .map { fieldError -> fieldError.field + " " + fieldError.defaultMessage }

        val errorToJsonString = ObjectMapper().writeValueAsString(fieldErrorMessage)
        val errorResponse = ErrorResponse(
            status = status.value(),
            code = status.name,
            reason = errorToJsonString,
            path = url,
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    /** Request Param Validation 예외 처리*/
    @ExceptionHandler(ConstraintViolationException::class)
    fun constraintViolationExceptionHandler(
        e: ConstraintViolationException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse?>? {
        val bindingErrors: MutableMap<String?, Any> = HashMap()
        e.constraintViolations.forEach {
                constraintViolation ->
            val propertyPath = constraintViolation.propertyPath.toString().split(".").dropLastWhile { it.isEmpty() }
            val path = propertyPath.takeLast(1).firstOrNull()
            bindingErrors[path] = constraintViolation.message
        }
        val errorReason = ErrorReason.of(
            status = BAD_REQUEST,
            code = bindingErrors.toString(),
            reason = bindingErrors.toString(),
        )
        val errorResponse = ErrorResponse.of(errorReason, request.requestURL.toString())
        return ResponseEntity.status(errorReason.status)
            .body(errorResponse)
    }

    @ExceptionHandler(WhatNowDynamicException::class)
    fun WhatnowDynamicExceptionHandler(
        e: WhatNowDynamicException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse?>? {
        val errorResponse = ErrorResponse(
            e.status,
            e.code,
            e.reason,
            path = request.requestURL.toString(),
        )
        return ResponseEntity.status(HttpStatus.valueOf(e.status)).body<ErrorResponse>(errorResponse)
    }

    @ExceptionHandler(WhatnowCodeException::class)
    fun codeExceptionHandler(
        e: WhatnowCodeException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse?>? {
//        val code: BaseErrorCode? = e.errorCode
        val errorReason: ErrorReason = e.errorReason
        val errorResponse = ErrorResponse.of(errorReason, request.requestURL.toString())
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.status))
            .body<ErrorResponse>(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    private fun handleException(
        e: Exception,
        request: HttpServletRequest?,
    ): ResponseEntity<ErrorResponse?> {
//        val cachingRequest = request as ContentCachingRequestWrapper?
        val cachingRequest = request as ContentCachingRequestWrapper
        val userId: Long = SecurityUtils.currentUserId
        val url = UriComponentsBuilder.fromHttpRequest(ServletServerHttpRequest(request))
            .build()
            .toUriString()

        val internalServerError = INTERNAL_SERVER_ERROR
        val errorResponse = ErrorResponse(
            internalServerError.status,
            internalServerError.code,
            internalServerError.reason,
            url,
        )
        slackProvider.execute(cachingRequest, e, userId)
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.status))
            .body<ErrorResponse>(errorResponse)
    }
}
