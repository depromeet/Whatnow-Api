package com.depromeet.whatnow.domains.image.service

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.image.adapter.PromiseImageAdapter
import com.depromeet.whatnow.domains.image.adapter.UserImageAdapter
import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.domain.UserImage
import com.depromeet.whatnow.domains.image.exception.CancelledUserUploadException
import com.depromeet.whatnow.domains.image.exception.InvalidCommentTypeException
import com.depromeet.whatnow.domains.image.exception.UploadBeforeTrackingException
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ImageDomainService(
    val promiseImageAdapter: PromiseImageAdapter,
    val userImageAdapter: UserImageAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val springEnvironmentHelper: SpringEnvironmentHelper,
) {
    @Transactional
    fun promiseImageUploadSuccess(
        userId: Long,
        promiseId: Long,
        imageKey: String,
        promiseImageCommentType: PromiseImageCommentType
    ) {
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        validatePromiseUserType(promiseUser.promiseUserType!!, promiseImageCommentType)

        val imageUrl = IMAGE_DOMAIN + "/" + springEnvironmentHelper.activeProfile + "/" + "promise/$promiseId/$imageKey"
        promiseImageAdapter.save(
            PromiseImage.of(promiseId, userId, imageUrl, imageKey, promiseImageCommentType),
        )
    }

    @Transactional
    fun userImageUploadSuccess(userId: Long, imageKey: String) {
        val imageUrl = IMAGE_DOMAIN + "/" + springEnvironmentHelper.activeProfile + "/" + "user/$userId/$imageKey"
        userImageAdapter.save(UserImage.of(userId, imageUrl, imageKey))
    }

    private fun validatePromiseUserType(promiseUserType: PromiseUserType, promiseImageCommentType: PromiseImageCommentType) {
        when (promiseUserType) {
            PromiseUserType.READY -> throw UploadBeforeTrackingException.EXCEPTION
            PromiseUserType.CANCEL -> throw CancelledUserUploadException.EXCEPTION
            PromiseUserType.WAIT, PromiseUserType.LATE -> {
                if (promiseImageCommentType.promiseUserType != promiseUserType) {
                    throw InvalidCommentTypeException.EXCEPTION
                }
            }
        }
    }
}
