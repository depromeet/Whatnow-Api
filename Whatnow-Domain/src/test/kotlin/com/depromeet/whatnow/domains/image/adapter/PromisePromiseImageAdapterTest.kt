package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.repository.PromiseImageRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class PromisePromiseImageAdapterTest {
    @Mock
    lateinit var promiseImageRepository: PromiseImageRepository

    @InjectMocks
    lateinit var promiseImageAdapter: PromiseImageAdapter

    @Test
    fun `약속 이미지 저장 시 정상적으로 저장된다`() {
        // given
        val promiseImage = PromiseImage.of(1, 1, "imageUri", "imageKey", ImageFileExtension.JPEG, PromiseImageCommentType.RUNNING)
        given(promiseImageRepository.save(Mockito.any(PromiseImage::class.java)))
            .willReturn(promiseImage)

        // when
        val savedPromiseImage = promiseImageAdapter.save(promiseImage)

        // then
        assertEquals(savedPromiseImage.userId, 1)
        assertEquals(savedPromiseImage.promiseId, 1)
        assertEquals(savedPromiseImage.uri, "imageUri")
        assertEquals(savedPromiseImage.imageKey, "imageKey")
        assertEquals(savedPromiseImage.promiseImageCommentType, PromiseImageCommentType.RUNNING)
    }
}
