package com.depromeet.whatnow.domains.picture.adapter

import com.depromeet.whatnow.domains.picture.domain.Picture
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.repository.PictureRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentCaptor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class PictureAdapterTest {
    @Mock
    lateinit var pictureRepository: PictureRepository

    @InjectMocks
    lateinit var pictureAdapter: PictureAdapter

    @Test
    fun `Picture 저장 시 정상적으로 저장된다`() {
        val captor: ArgumentCaptor<Picture> = ArgumentCaptor.forClass(Picture::class.java)

        // when
        pictureAdapter.save(1, 1, "imageUrl", "imageKey", PictureCommentType.RUNNING)

        // then
        then(pictureRepository).should(Mockito.times(1)).save(captor.capture())
    }
}
