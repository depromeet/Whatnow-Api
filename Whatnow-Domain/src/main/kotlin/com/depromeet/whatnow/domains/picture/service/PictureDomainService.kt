package com.depromeet.whatnow.domains.picture.service

import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.picture.adapter.PictureAdapter
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.exception.CancelledUserUploadException
import com.depromeet.whatnow.domains.picture.exception.InvalidCommentTypeException
import com.depromeet.whatnow.domains.picture.exception.UploadBeforeTrackingException
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PictureDomainService(
    val pictureAdapter: PictureAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
) {
    @Transactional
    fun promiseUploadImageSuccess(userId: Long, promiseId: Long, imageKey: String, pictureCommentType: PictureCommentType) {
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        validatePromiseUserType(promiseUser.promiseUserType!!, pictureCommentType)

        val imageUrl = IMAGE_DOMAIN + "promise/$promiseId/$imageKey"
        pictureAdapter.saveForPromise(userId, promiseId, imageUrl, imageKey, pictureCommentType)
    }

    fun userUploadImageSuccess(userId: Long, imageKey: String) {
        val user = userAdapter.queryUser(userId)
        val imageUrl = IMAGE_DOMAIN + "user/$userId/$imageKey"
        pictureAdapter.saveForUser(userId, imageUrl, imageKey)
        user.updateProfileImg(imageUrl)
    }

    private fun validatePromiseUserType(promiseUserType: PromiseUserType, pictureCommentType: PictureCommentType) {
        when (promiseUserType) {
            PromiseUserType.READY -> throw UploadBeforeTrackingException.EXCEPTION
            PromiseUserType.CANCEL -> throw CancelledUserUploadException.EXCEPTION
            PromiseUserType.WAIT, PromiseUserType.LATE -> {
                if (pictureCommentType.promiseUserType != promiseUserType) {
                    throw InvalidCommentTypeException.EXCEPTION
                }
            }
        }
    }
}
