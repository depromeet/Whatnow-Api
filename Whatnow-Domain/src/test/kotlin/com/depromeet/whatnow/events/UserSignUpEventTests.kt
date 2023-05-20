package com.depromeet.whatnow.events

import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.service.UserDomainService
import com.depromeet.whatnow.events.handler.UserSignUpEventHandler
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@DomainIntegrateSpringBootTest
class UserSignUpEventTests {

    @Autowired lateinit var userDomainService: UserDomainService

    @MockBean lateinit var userSignUpEventHandler: UserSignUpEventHandler

    @Test
    fun `유저회원가입시 유저등록 이벤트가 발행되어야한다`() {
        // when
        userDomainService.upsertUser("", "", true, OauthInfo("", OauthProvider.KAKAO), "")
        // then
        then(userSignUpEventHandler).should(Mockito.times(1)).handleRegisterUserEvent(any())
    }
}
