package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class PromiseUserRecordUseCaseTest {

    @Mock
    private lateinit var promiseUserDomainService: PromiseUserDomainService

    private lateinit var promiseUserReadUseCase: PromiseUserReadUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        promiseUserReadUseCase = PromiseUserReadUseCase(promiseUserDomainService)
    }

    @Test
    fun PromiseId로_참석한_PromiseUsers를_조회한다() {
        val promiseId = 1L
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, CoordinateVo(100.4, 1.2), PromiseUserType.READY),
            PromiseUser(2L, 2L, CoordinateVo(123.4, 132.6), PromiseUserType.LATE),
            PromiseUser(3L, 1L, CoordinateVo(123.445, 6789.612), PromiseUserType.WAIT),
        )
        `when`(promiseUserDomainService.findByPromiseId(promiseId)).thenReturn(promiseUserList)

        val result = promiseUserReadUseCase.findByPromiseId(promiseId)

        assertEquals(promiseUserList.size, result.size)
    }

    @Test
    fun PromiseUser_생성_테스트() {
        val userId = 2L
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, CoordinateVo(100.4, 1.2), PromiseUserType.READY),
            PromiseUser(2L, 2L, CoordinateVo(123.4, 132.6), PromiseUserType.LATE),
            PromiseUser(3L, 1L, CoordinateVo(123.445, 6789.612), PromiseUserType.WAIT),
        )
        `when`(promiseUserDomainService.findByUserId(userId)).thenReturn(promiseUserList)

        val result = promiseUserReadUseCase.findByUserIdOnReady(userId)

        assertEquals(1, result.size)
    }

    @Test
    fun 입력받은_status로_조회한다() {
        val userId = 1L
        val status = "READY"
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, CoordinateVo(100.4, 1.2), PromiseUserType.READY),
            PromiseUser(2L, 2L, CoordinateVo(123.4, 132.6), PromiseUserType.READY),
            PromiseUser(3L, 1L, CoordinateVo(123.445, 6789.612), PromiseUserType.WAIT),
        )
        `when`(promiseUserDomainService.findByUserId(userId)).thenReturn(promiseUserList)

        val result = promiseUserReadUseCase.findByUserIdWithStatus(userId, status)

        assertEquals(2, result.size)
    }
}
