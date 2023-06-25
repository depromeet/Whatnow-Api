package com.depromeet.whatnow.domains.image.service

import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.image.adapter.ImageAdapter
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
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
    val imageAdapter: ImageAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val springEnvironmentHelper: SpringEnvironmentHelper,
) {
    @Transactional
    fun promiseUploadImageSuccess(userId: Long, promiseId: Long, imageKey: String, imageCommentType: ImageCommentType) {
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        validatePromiseUserType(promiseUser.promiseUserType!!, imageCommentType)

        val imageUrl = IMAGE_DOMAIN + springEnvironmentHelper.getActiveProfile + "/" + "promise/$promiseId/$imageKey"
        imageAdapter.saveForPromise(userId, promiseId, imageUrl, imageKey, imageCommentType)
    }

    fun userUploadImageSuccess(userId: Long, imageKey: String) {
        val user = userAdapter.queryUser(userId)
        val imageUrl = IMAGE_DOMAIN + springEnvironmentHelper.getActiveProfile + "/" + "user/$userId/$imageKey"
        imageAdapter.saveForUser(userId, imageUrl, imageKey)
        user.updateProfileImg(imageUrl)
    }

    private fun validatePromiseUserType(promiseUserType: PromiseUserType, imageCommentType: ImageCommentType) {
        when (promiseUserType) {
            PromiseUserType.READY -> throw UploadBeforeTrackingException.EXCEPTION
            PromiseUserType.CANCEL -> throw CancelledUserUploadException.EXCEPTION
            PromiseUserType.WAIT, PromiseUserType.LATE -> {
                if (imageCommentType.promiseUserType != promiseUserType) {
                    throw InvalidCommentTypeException.EXCEPTION
                }
            }
        }
    }
}
