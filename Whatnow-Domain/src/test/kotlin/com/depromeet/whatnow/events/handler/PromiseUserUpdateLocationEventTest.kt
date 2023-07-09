package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.READY
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@DomainIntegrateSpringBootTest
class PromiseUserUpdateLocationEventTest {
    @Autowired
    private lateinit var promiseUserDomainService: PromiseUserDomainService

    @MockBean
    private lateinit var promiseUserAdaptor: PromiseUserAdaptor

    @MockBean
    private lateinit var promiseUserUpdateLocationEventHandler: PromiseUserUpdateLocationEventHandler

    @Test
    fun updatePromiseUserLocation() {
        val promiseUsers = listOf(
            PromiseUser(1L, 1L, CoordinateVo(123.4, 234.5), READY),
            PromiseUser(2L, 1L, CoordinateVo(123.4, 236.9), READY),
            PromiseUser(3L, 1L, CoordinateVo(123.4, 234.7), READY),
        )
        val promiseUsersByPromiseId = listOf(
            PromiseUser(1L, 1L, CoordinateVo(123.4, 234.5), READY),
            PromiseUser(1L, 2L, CoordinateVo(234.4, 24.5), READY),
            PromiseUser(1L, 3L, CoordinateVo(345.4, 4.5), READY),
        )

        given(promiseUserDomainService.findByUserId(1)).willReturn(promiseUsers)
        given(promiseUserDomainService.findByPromiseId(1)).willReturn(promiseUsersByPromiseId)

        promiseUserDomainService.updateLocation(1L, 1L, CoordinateVo(234.52, 65.24))
        // PromiseUserUpdateLocationEvent 가 발행됐는지 검증
        then(promiseUserUpdateLocationEventHandler).should(Mockito.times(1)).handlePromiseUserUpdateLocationEvent(any())
    }
}
