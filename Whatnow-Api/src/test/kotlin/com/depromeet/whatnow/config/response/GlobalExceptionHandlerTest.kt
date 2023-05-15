package com.depromeet.whatnow.config.response

import com.depromeet.whatnow.WhatnowApiApplication
import com.depromeet.whatnow.WhatnowCommonApplication
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.CodeException
import com.depromeet.whatnow.exception.GlobalErrorCode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.context.annotation.ComponentScan
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletRequest
@ComponentScan(basePackageClasses = [WhatnowApiApplication::class, WhatnowCommonApplication::class])
class GlobalExceptionHandlerTest {
    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Mock
    lateinit var request: HttpServletRequest

//    @Test
//    fun CodeExceptionHandlerTest() {
//        val internalServerError = GlobalErrorCode.INTERNAL_SERVER_ERROR
//        val reason = ErrorReason(internalServerError.status,internalServerError.code,internalServerError.reason)
//        // Arrange
//        val exception = CodeException(internalServerError)
//
//        // Act
//        val responseEntity = GlobalExceptionHandler().codeExceptionHandler(exception, request)
//
//        // Assert
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity?.statusCode)
//    }
}
