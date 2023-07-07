package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.DomainIntegrateSpringBootTest
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.service.ImageDomainService
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean

@DomainIntegrateSpringBootTest
class PromiseImageRegisterEventHandlerTest {
    @Autowired
    lateinit var imageDomainService: ImageDomainService

    @MockBean
    lateinit var promiseUserAdaptor: PromiseUserAdaptor

    @MockBean
    lateinit var userAdapter: UserAdapter

    @MockBean
    lateinit var imageRegisterEventHandler: ImageRegisterEventHandler

    @Test
    fun `약속 이미지 등록 성공 시 이미지 등록 이벤트가 발행되어야한다`() {
        // given
        val promiseUser = PromiseUser(1, 1, CoordinateVo(1.0, 1.0), PromiseUserType.LATE)
        given(promiseUserAdaptor.findByPromiseIdAndUserId(1, 1)).willReturn(promiseUser)

        // when
        imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", ImageFileExtension.JPEG, PromiseImageCommentType.RUNNING)

        // then
        then(imageRegisterEventHandler).should(Mockito.times(1)).handleRegisterPictureEvent(any())
    }
}
