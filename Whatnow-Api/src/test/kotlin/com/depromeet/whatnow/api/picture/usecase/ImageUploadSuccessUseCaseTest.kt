package com.depromeet.whatnow.api.picture.usecase

import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
import com.depromeet.whatnow.domains.image.service.ImageDomainService
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockitoExtension::class)
class ImageUploadSuccessUseCaseTest {
    @Mock
    lateinit var imageDomainService: ImageDomainService

    @InjectMocks
    lateinit var imageUploadSuccessUseCase: ImageUploadSuccessUseCase

    @BeforeEach
    fun setup() {
        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken("1", null, setOf(SimpleGrantedAuthority("ROLE_USER")))
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `약속 이미지 업로드 성공 요청시 정상적이라면 에러가 발생하지 않는다`() {
        // given

        // when

        // then
        assertThatCode {
            imageUploadSuccessUseCase.promiseUploadImageSuccess(1, "imageKey", ImageCommentType.SORRY_LATE)
        }.doesNotThrowAnyException()
    }

    @Test
    fun `유저 프로필 업로드 성공 요청시 정상적이라면 에러가 발생하지 않는다`() {
        // given

        // when

        // then
        assertThatCode {
            imageUploadSuccessUseCase.userUploadImageSuccess("imageKey")
        }.doesNotThrowAnyException()
    }
}
