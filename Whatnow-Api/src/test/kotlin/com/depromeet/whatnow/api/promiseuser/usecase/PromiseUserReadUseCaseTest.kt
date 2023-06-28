package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PromiseUserReadUseCaseTest {
    @Mock
    private lateinit var promiseUserDomainService: PromiseUserDomainService

    private lateinit var promiseUserReadUseCase: PromiseUserReadUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        promiseUserReadUseCase = PromiseUserReadUseCase(promiseUserDomainService)
    }

    @Test
    fun `PromiseId로_1개의_회원을_조회할_수_있다`() {
        val promiseId = 1L
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, CoordinateVo(100.4, 1.2), PromiseUserType.READY),
            PromiseUser(2L, 2L, CoordinateVo(123.4, 132.6), PromiseUserType.LATE),
            PromiseUser(3L, 1L, CoordinateVo(123.445, 6789.612), PromiseUserType.WAIT),
        )
        `when`(promiseUserDomainService.findByPromiseId(promiseId)).thenReturn(promiseUserList)

        val result = promiseUserReadUseCase.findByPromiseId(promiseId)

        val expected = promiseUserList.map { PromiseUserDto.from(it) }
        assertEquals(expected, result)
    }

    @Test
    fun `userId로_PromiseUser_Type이_READY인_PromiseUser를_조회할_수_있다`() {
        val userId = 2L
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, CoordinateVo(100.4, 1.2), PromiseUserType.READY),
            PromiseUser(2L, 2L, CoordinateVo(123.4, 132.6), PromiseUserType.LATE),
            PromiseUser(3L, 1L, CoordinateVo(123.445, 6789.612), PromiseUserType.WAIT),
        )
        `when`(promiseUserDomainService.findByUserId(userId)).thenReturn(promiseUserList)

        val result = promiseUserReadUseCase.findByUserIdOnReady(userId)

        val expected = promiseUserList
            .filter { it.promiseUserType == PromiseUserType.READY }
            .map { PromiseUserDto.from(it) }
        assertEquals(expected, result)
    }
}
