package com.depromeet.whatnow.domains.image.service

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.image.adapter.PromiseImageAdapter
import com.depromeet.whatnow.domains.image.adapter.UserImageAdapter
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.exception.CancelledUserUploadException
import com.depromeet.whatnow.domains.image.exception.InvalidCommentTypeException
import com.depromeet.whatnow.domains.image.exception.UploadBeforeTrackingException
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class PromiseImageDomainServiceTest {
    @Mock
    lateinit var promiseImageAdapter: PromiseImageAdapter

    @Mock
    lateinit var promiseUserAdapter: PromiseUserAdaptor

    @Mock
    lateinit var userImageAdapter: UserImageAdapter

    @Mock
    lateinit var userAdapter: UserAdapter

    @Mock
    lateinit var springEnvironmentHelper: SpringEnvironmentHelper

    @InjectMocks
    lateinit var imageDomainService: ImageDomainService

    @Test
    fun `약속 이미지 업로드 성공 요청시 정상적이라면 에러를 반환하지 않는다`() {
        // given
        val promiseUser = PromiseUser(
            promiseId = 1,
            userId = 1,
            userLocation = CoordinateVo(1.0, 1.0),
            promiseUserType = PromiseUserType.LATE,
        )
        given(promiseUserAdapter.findByPromiseIdAndUserId(anyLong(), anyLong()))
            .willReturn(promiseUser)

        // when, then
        Assertions.assertThatCode {
            imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", PromiseImageCommentType.RUNNING)
        }.doesNotThrowAnyException()
    }

    @Test
    fun `약속 이미지 업로드 성공 요청시 유저가 READY 상태라면 예외가 발생한다`() {
        // given
        val promiseUser = PromiseUser(
            promiseId = 1,
            userId = 1,
            userLocation = CoordinateVo(1.0, 1.0),
            promiseUserType = PromiseUserType.READY,
        )
        given(promiseUserAdapter.findByPromiseIdAndUserId(anyLong(), anyLong()))
            .willReturn(promiseUser)

        // when, then
        Assertions.assertThatThrownBy {
            imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", PromiseImageCommentType.RUNNING)
        }.isInstanceOf(UploadBeforeTrackingException::class.java)
    }

    @Test
    fun `약속 이미지 업로드 성공 요청시 유저가 CANCEL 상태라면 예외가 발생한다`() {
        // given
        val promiseUser = PromiseUser(
            promiseId = 1,
            userId = 1,
            userLocation = CoordinateVo(1.0, 1.0),
            promiseUserType = PromiseUserType.CANCEL,
        )
        given(promiseUserAdapter.findByPromiseIdAndUserId(anyLong(), anyLong()))
            .willReturn(promiseUser)

        // when, then
        Assertions.assertThatThrownBy {
            imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", PromiseImageCommentType.RUNNING)
        }.isInstanceOf(CancelledUserUploadException::class.java)
    }

    @Test
    fun `약속 이미지 업로드 성공 요청시 LATE 유저가 WAIT 타입의 코멘트를 입력 할 경우 예외가 발생한다`() {
        // given
        val promiseUser = PromiseUser(
            promiseId = 1,
            userId = 1,
            userLocation = CoordinateVo(1.0, 1.0),
            promiseUserType = PromiseUserType.LATE,
        )
        given(promiseUserAdapter.findByPromiseIdAndUserId(anyLong(), anyLong()))
            .willReturn(promiseUser)

        // when, then
        Assertions.assertThatThrownBy {
            imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", PromiseImageCommentType.DID_YOU_COME)
        }.isInstanceOf(InvalidCommentTypeException::class.java)
    }

    @Test
    fun `약속 이미지 업로드 성공 요청시 WAIT 유저가 LATE 타입의 코멘트를 입력 할 경우 예외가 발생한다`() {
        // given
        val promiseUser = PromiseUser(
            promiseId = 1,
            userId = 1,
            userLocation = CoordinateVo(1.0, 1.0),
            promiseUserType = PromiseUserType.WAIT,
        )
        given(promiseUserAdapter.findByPromiseIdAndUserId(anyLong(), anyLong()))
            .willReturn(promiseUser)

        // when, then
        Assertions.assertThatThrownBy {
            imageDomainService.promiseImageUploadSuccess(1, 1, "imageKey", PromiseImageCommentType.WAIT_A_BIT)
        }.isInstanceOf(InvalidCommentTypeException::class.java)
    }
}
