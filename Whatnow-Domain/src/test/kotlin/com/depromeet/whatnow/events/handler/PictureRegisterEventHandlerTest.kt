package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.picture.service.PictureDomainService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@DomainIntegrateSpringBootTest
class PictureRegisterEventHandlerTest {
    @Autowired
    lateinit var pictureDomainService: PictureDomainService

    @MockBean
    lateinit var pictureRegisterEventHandler: PictureRegisterEventHandler

    @Test
    fun `사진 등록 성공 시 사진등록 이벤트가 발행되어야한다`() {
        // when
        pictureDomainService.successUploadImage(1, 1, "imageKey")
        // then
        then(pictureRegisterEventHandler).should(Mockito.times(1)).handleRegisterPictureEvent(any())
    }
}
