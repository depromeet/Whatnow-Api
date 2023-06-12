package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.config.s3.ImageUrlDto
import com.depromeet.whatnow.config.s3.S3UploadPresignedUrlService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class GetPresignedUrlUseCaseTest {
    @Mock
    lateinit var presignedUrlService: S3UploadPresignedUrlService

    @InjectMocks
    lateinit var getPresignedUrlUseCase: GetPresignedUrlUseCase

    @Test
    fun `PresignUrl 을 요청하면 url 을 반환한다`() {
        // given
        given(presignedUrlService.forPromise(1, ImageFileExtension.JPEG)).willReturn(
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
}
