package com.depromeet.whatnow.domains.picture.adapter

import com.depromeet.whatnow.domains.picture.domain.Picture
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.domain.PictureType
import com.depromeet.whatnow.domains.picture.repository.PictureRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class PictureAdapterTest {
    @Mock
    lateinit var pictureRepository: PictureRepository

    @InjectMocks
    lateinit var pictureAdapter: PictureAdapter

    @Test
    fun `약속 이미지 Picture 저장 시 정상적으로 저장된다`() {
        given(pictureRepository.save(Mockito.any(Picture::class.java)))
            .willReturn(Picture.createForPromise(1, 1, "imageUrl", "imageKey", PictureCommentType.RUNNING))

        // when
        val picture = pictureAdapter.saveForPromise(1, 1, "imageUrl", "imageKey", PictureCommentType.RUNNING)

        // then
        assertEquals(picture.userId, 1)
        assertEquals(picture.promiseId, 1)
        assertEquals(picture.url, "imageUrl")
        assertEquals(picture.uuid, "imageKey")
        assertEquals(picture.pictureType, PictureType.PROMISE)
        assertEquals(picture.pictureCommentType, PictureCommentType.RUNNING)
    }

    @Test
    fun `유저 프로필 Picture 저장 시 정상적으로 저장된다`() {
        given(pictureRepository.save(Mockito.any(Picture::class.java)))
            .willReturn(Picture.createForUser(1, "imageUrl", "imageKey"))

        // when
        val picture = pictureAdapter.saveForUser(1, "imageUrl", "imageKey")

        // then
        assertEquals(picture.userId, 1)
        assertEquals(picture.promiseId, 0)
        assertEquals(picture.url, "imageUrl")
        assertEquals(picture.uuid, "imageKey")
        assertEquals(picture.pictureType, PictureType.USER)
        assertEquals(picture.pictureCommentType, PictureCommentType.NONE)
    }
}
