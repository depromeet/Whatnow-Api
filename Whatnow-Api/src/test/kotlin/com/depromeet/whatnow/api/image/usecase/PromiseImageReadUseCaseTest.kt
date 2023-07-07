package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.service.ImageDomainService
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockitoExtension::class)
class PromiseImageReadUseCaseTest {
    @Mock
    lateinit var imageDomainService: ImageDomainService

    @Mock
    lateinit var promiseUserDomainService: PromiseUserDomainService

    @InjectMocks
    lateinit var promiseImageReadUseCase: PromiseImageReadUseCase

    @BeforeEach
    fun setup() {
        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken("1", null, setOf(SimpleGrantedAuthority("ROLE_USER")))
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `약속 이미지를 모두 조회하면 WAIT, LATE로 구분되며, LATE는 가장최신 한게만 존재해야한다`() {
        // given
        val imageKey1 = "7e74f27e-0252-44cb-a2f2-17c1b5cce9c3"
        val promiseImage1 = PromiseImage(
            userId = 1,
            promiseId = 1,
            uri = "https://image/whatnow.kr/$imageKey1.jpg",
            imageKey = imageKey1,
            promiseImageCommentType = PromiseImageCommentType.DID_YOU_COME,
        )
        val imageKey2 = "7e74f27e-0252-23ds-a2f2-58s1b5cce9c3"
        val promiseImage2 = PromiseImage(
            userId = 1,
            promiseId = 1,
            uri = "https://image/whatnow.kr/$imageKey2.jpg",
            imageKey = imageKey2,
            promiseImageCommentType = PromiseImageCommentType.DID_YOU_COME,
        )
        val promiseUser1 = PromiseUser(
            userId = 1,
            promiseId = 1,
            promiseUserType = PromiseUserType.WAIT,
        )
        given(promiseUserDomainService.findByPromiseIdAndUserId(1, 1)).willReturn(promiseUser1)

        val imageKey3 = "7e74f27e-0252-23ds-a2f2-58s1b5cce9c3"
        val promiseImage3 = PromiseImage(
            userId = 2,
            promiseId = 1,
            uri = "https://image/whatnow.kr/$imageKey3.jpg",
            imageKey = imageKey3,
            promiseImageCommentType = PromiseImageCommentType.RUNNING,
        )
        val imageKey4 = "7e74f27e-0252-23ds-a2f2-o50s1b5cce9c3"
        val promiseImage4 = PromiseImage(
            userId = 2,
            promiseId = 1,
            uri = "https://image/whatnow.kr/$imageKey4.jpg",
            imageKey = imageKey4,
            promiseImageCommentType = PromiseImageCommentType.RUNNING,
        )
        val promiseUser2 = PromiseUser(
            userId = 2,
            promiseId = 1,
            promiseUserType = PromiseUserType.LATE,
        )
        given(promiseUserDomainService.findByPromiseIdAndUserId(1, 2)).willReturn(promiseUser2)

        given(imageDomainService.getImagesByPromiseId(1)).willReturn(listOf(promiseImage1, promiseImage2, promiseImage3, promiseImage4))

        // when
        val promiseImages = promiseImageReadUseCase.getLateAndWaitImagesByPromiseId(1)

        // then
        // 이미지를 조회하는 사람의 타입이 같아야 한다
        assertEquals(promiseUser1.promiseUserType, promiseImages.promiseUserType)

        // 지각한 사람의 이미지는 모두 조회되어야 한다
        promiseImages.promiseImages[PromiseUserType.LATE]?.let { assertEquals(2, it.size) }

        // 기다리는 사람의 이미지는 가장 최신 한개만 조회되어야 한다
        promiseImages.promiseImages[PromiseUserType.WAIT]?.let { assertEquals(1, it.size) }
    }
}
