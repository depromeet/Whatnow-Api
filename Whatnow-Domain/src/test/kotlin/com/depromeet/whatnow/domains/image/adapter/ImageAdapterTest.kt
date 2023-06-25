package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.domains.image.domain.Image
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
import com.depromeet.whatnow.domains.image.domain.ImageType
import com.depromeet.whatnow.domains.image.repository.ImageRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ImageAdapterTest {
    @Mock
    lateinit var imageRepository: ImageRepository

    @InjectMocks
    lateinit var imageAdapter: ImageAdapter

    @Test
    fun `약속 이미지 Picture 저장 시 정상적으로 저장된다`() {
        given(imageRepository.save(Mockito.any(Image::class.java)))
            .willReturn(Image.createForPromise(1, 1, "imageUrl", "imageKey", ImageCommentType.RUNNING))

        // when
        val picture = imageAdapter.saveForPromise(1, 1, "imageUrl", "imageKey", ImageCommentType.RUNNING)

        // then
        assertEquals(picture.userId, 1)
        assertEquals(picture.promiseId, 1)
        assertEquals(picture.url, "imageUrl")
        assertEquals(picture.uuid, "imageKey")
        assertEquals(picture.pictureType, ImageType.PROMISE)
        assertEquals(picture.pictureCommentType, ImageCommentType.RUNNING)
    }

    @Test
    fun `유저 프로필 Picture 저장 시 정상적으로 저장된다`() {
        given(imageRepository.save(Mockito.any(Image::class.java)))
            .willReturn(Image.createForUser(1, "imageUrl", "imageKey"))

        // when
        val picture = imageAdapter.saveForUser(1, "imageUrl", "imageKey")

        // then
        assertEquals(picture.userId, 1)
        assertEquals(picture.promiseId, 0)
        assertEquals(picture.url, "imageUrl")
        assertEquals(picture.uuid, "imageKey")
        assertEquals(picture.pictureType, ImageType.USER)
        assertEquals(picture.pictureCommentType, ImageCommentType.NONE)
    }
}
