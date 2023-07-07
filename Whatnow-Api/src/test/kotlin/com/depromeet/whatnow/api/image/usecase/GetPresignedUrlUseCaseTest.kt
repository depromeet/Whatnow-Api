package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.config.s3.ImageUrlDto
import com.depromeet.whatnow.config.s3.S3Service
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
import org.springframework.security.test.context.support.WithMockUser

@ExtendWith(MockitoExtension::class)
class GetPresignedUrlUseCaseTest {
    @Mock
    lateinit var presignedUrlService: S3Service

    @InjectMocks
    lateinit var getPresignedUrlUseCase: GetPresignedUrlUseCase

    @BeforeEach
    fun setup() {
        val securityContext = SecurityContextHolder.createEmptyContext()
        val authentication = UsernamePasswordAuthenticationToken("1", null, setOf(SimpleGrantedAuthority("ROLE_USER")))
        securityContext.authentication = authentication
        SecurityContextHolder.setContext(securityContext)
    }

    @Test
    fun `약속 이미지 PresignUrl 을 요청하면 url 을 반환한다`() {
        // given
        given(presignedUrlService.getPresignedUrlForPromise(1, ImageFileExtension.JPEG)).willReturn(
            ImageUrlDto(
                url = "https://whatnow.kr/1.jpg",
                key = "1.jpg",
            ),
        )
        // when
        val imageUrlResponse = getPresignedUrlUseCase.forPromise(1, ImageFileExtension.JPEG)

        // then
        assertEquals("https://whatnow.kr/1.jpg", imageUrlResponse.presignedUrl)
        assertEquals("1.jpg", imageUrlResponse.key)
    }

    @Test
    @WithMockUser(username = "1")
    fun `유저 프로필 PresignUrl 을 요청하면 url 을 반환한다`() {
        // given
        given(presignedUrlService.getPresignedUrlForUser(1, ImageFileExtension.JPEG)).willReturn(
            ImageUrlDto(
                url = "https://whatnow.kr/1.jpg",
                key = "1.jpg",
            ),
        )
        // when
        val imageUrlResponse = getPresignedUrlUseCase.forUser(ImageFileExtension.JPEG)

        // then
        assertEquals("https://whatnow.kr/1.jpg", imageUrlResponse.presignedUrl)
        assertEquals("1.jpg", imageUrlResponse.key)
    }
}
