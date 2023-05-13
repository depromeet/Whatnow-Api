package com.depromeet.whatnow.config.response

import com.depromeet.whatnow.consts.BAD_REQUEST
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.dto.ErrorResponse
import com.depromeet.whatnow.exception.BaseErrorCode
import com.depromeet.whatnow.exception.CodeException
import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatNowDynamicException
import com.fasterxml.jackson.databind.ObjectMapper
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
import java.util.List
import java.util.function.Consumer
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolation
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

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

        val errorResponse: ErrorResponse = ErrorResponse(status.value(), ex.message, url)
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
    fun ConstraintViolationExceptionHandler(
        e: ConstraintViolationException, request: HttpServletRequest
    ): ResponseEntity<ErrorResponse?>? {
        val bindingErrors: MutableMap<String?, Any> = HashMap()
        e.constraintViolations
            .forEach(
                Consumer { constraintViolation: ConstraintViolation<*> ->
                    val propertyPath = List.of(
                        *constraintViolation
                            .propertyPath
                            .toString()
                            .split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    )
                    val path = propertyPath.stream()
                        .skip(propertyPath.size - 1L)
                        .findFirst()
                        .orElse(null)
                    bindingErrors[path] = constraintViolation.message
                })
        val errorReason = ErrorReason.builder()
            .code(BAD_REQUEST.toString())
            .status(BAD_REQUEST)
            .reason(bindingErrors.toString())
            .build()
        val errorResponse = ErrorResponse(errorReason, request.requestURL.toString())
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.status!!))
            .body<ErrorResponse>(errorResponse)
    }
    @ExceptionHandler(WhatNowDynamicException::class)
    fun WhatnowDynamicExceptionHandler(
        e: WhatNowDynamicException, request: HttpServletRequest
    ): ResponseEntity<ErrorResponse?>? {
        val errorResponse = ErrorResponse(
            e.status,
            e.code,
            e.reason,
            path = request.requestURL.toString()
        )
        return ResponseEntity.status(HttpStatus.valueOf(e.status)).body<ErrorResponse>(errorResponse)
    }

    @ExceptionHandler(CodeException::class)
    fun CodeExceptionHandler(
        e: CodeException, request: HttpServletRequest
    ): ResponseEntity<ErrorResponse?>? {
        val code: BaseErrorCode? = e.errorCode
        val errorReason: ErrorReason? = e.getErrorReason()
        val errorResponse = ErrorResponse(errorReason, request.requestURL.toString())
        return ResponseEntity.status(HttpStatus.valueOf(errorReason?.status!!))
            .body<ErrorResponse>(errorResponse)
    }
    @ExceptionHandler(Exception::class)
    private fun handleException(
        e: Exception?,
        request: HttpServletRequest?,
    ): ResponseEntity<ErrorResponse?> {
        val cachingRequest = request as ContentCachingRequestWrapper?
        val url = UriComponentsBuilder.fromHttpRequest(
            ServletServerHttpRequest(
                request!!,
            ),
        )
            .build()
            .toUriString()

        // ** 예시 ErrorCode입니다. 차후 수정 예정 */
        val internalServerError: GlobalErrorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR

        val errorResponse = ErrorResponse(
            status = internalServerError.status,
            code = internalServerError.code,
            reason = internalServerError.reason,
            path = url,
        )

        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.status!!))
            .body<ErrorResponse>(errorResponse)
    }
}
