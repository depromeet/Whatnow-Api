package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired

@DomainIntegrateSpringBootTest
class PromiseUserUpdateLocationEventHandlerTest {
    @Mock
    lateinit var promiseAdaptor: PromiseAdaptor

    @Mock
    lateinit var promiseUserAdaptor: PromiseUserAdaptor

    @Mock
    lateinit var promiseUserDomainService: PromiseUserDomainService

    @Autowired
    lateinit var promiseUserUpdateLocationEventHandler: PromiseUserUpdateLocationEventHandler

    @BeforeEach
    fun setUp() {
        promiseUserUpdateLocationEventHandler = PromiseUserUpdateLocationEventHandler(promiseAdaptor, promiseUserAdaptor, promiseUserDomainService)
    }

    @Test
    fun `약속 참여자 위치 업데이트 성공 시 10m 이내의 점일 경우에 true를 반환한다`() {
        // given
        val promiseUser = PromiseUser(1, 1, CoordinateVo(35.177303, 126.907874), PromiseUserType.LATE)
        // when
        val arrived = promiseUserUpdateLocationEventHandler.isArrived(
            promiseUser.userLocation,
            CoordinateVo(35.177069, 126.907654),
        )
        // then
        assertEquals(true, arrived)
    }
}
