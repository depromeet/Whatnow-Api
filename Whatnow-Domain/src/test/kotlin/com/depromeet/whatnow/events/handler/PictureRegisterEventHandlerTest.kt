package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.service.PictureDomainService
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@DomainIntegrateSpringBootTest
class PictureRegisterEventHandlerTest {
    @Autowired
    lateinit var pictureDomainService: PictureDomainService

    @MockBean
    lateinit var promiseUserAdaptor: PromiseUserAdaptor

    @MockBean
    lateinit var pictureRegisterEventHandler: PictureRegisterEventHandler

    @Test
    fun `사진 등록 성공 시 사진등록 이벤트가 발행되어야한다`() {
        // given
        val promiseUser = PromiseUser(1, 1, CoordinateVo(1.0, 1.0), PromiseUserType.LATE)
        given(promiseUserAdaptor.findByPromiseIdAndUserId(1, 1)).willReturn(promiseUser)

        // when
        pictureDomainService.promiseUploadImageSuccess(1, 1, "imageKey", PictureCommentType.RUNNING)

        // then
        then(pictureRegisterEventHandler).should(Mockito.times(1)).handleRegisterPictureEvent(any())
    }
}
