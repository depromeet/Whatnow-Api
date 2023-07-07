package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class PromiseUserDomainServiceTest {
    @Mock
    private lateinit var promiseUserAdaptor: PromiseUserAdaptor

    private lateinit var promiseUserDomainService: PromiseUserDomainService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        promiseUserDomainService = PromiseUserDomainService(promiseUserAdaptor = promiseUserAdaptor)
    }

    @Test
    fun `500미터 안에 인접해 있으면 도착했다 정의`() {
        val a = PromiseUser(userLocation = CoordinateVo(35.866334, 127.146223), promiseId = 1L, userId = 1L)
        val b = CoordinateVo(35.866355, 127.146230)
        // 실제 거리  : 2.41878882096224 m
//        when and return
        val arrived = promiseUserDomainService.isArrived(a, b)
        assertEquals(true, arrived)
    }
}
