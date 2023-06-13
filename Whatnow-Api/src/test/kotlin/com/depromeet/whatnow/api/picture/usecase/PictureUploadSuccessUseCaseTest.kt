package com.depromeet.whatnow.api.picture.usecase

import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.service.PictureDomainService
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
class PictureUploadSuccessUseCaseTest {
    @Mock
    lateinit var pictureDomainService: PictureDomainService

    @InjectMocks
    lateinit var pictureUploadSuccessUseCase: PictureUploadSuccessUseCase

    @BeforeEach
    fun setup() {
        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken("1", null, setOf(SimpleGrantedAuthority("ROLE_USER")))
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `이미지 업로드 성공 요청시 정상적이라면 에러가 발생하지 않는다`() {
        // given

        // when

        // then
        assertThatCode {
            pictureUploadSuccessUseCase.successUploadImage(1, "1", PictureCommentType.SORRY_LATE)
        }.doesNotThrowAnyException()
    }
}
