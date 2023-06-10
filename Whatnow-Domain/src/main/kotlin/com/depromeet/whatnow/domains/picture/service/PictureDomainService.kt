package com.depromeet.whatnow.domains.picture.service

import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.picture.adapter.PromiseAdapter
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType.*
import com.depromeet.whatnow.domains.picture.exception.CancelledUserUploadException
import com.depromeet.whatnow.domains.picture.exception.LateUserInvalidCommentException
import com.depromeet.whatnow.domains.picture.exception.UploadBeforeTrackingException
import com.depromeet.whatnow.domains.picture.exception.WaitUserInvalidCommentException
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PictureDomainService(
    val pictureAdapter: PromiseAdapter,
    val promiseUserAdapter: PromiseUserAdaptor
) {
    @Transactional
    fun successUploadImage(userId: Long, promiseId: Long, imageKey: String, pictureCommentType: PictureCommentType) {
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        when (promiseUser.promiseUserType) {
            READY -> throw UploadBeforeTrackingException.EXCEPTION
            CANCEL -> throw CancelledUserUploadException.EXCEPTION
            WAIT -> handleWaitUser(pictureCommentType)
            LATE -> handleLateUser(pictureCommentType)
        }

        val imageUrl = IMAGE_DOMAIN + "promise/$promiseId/$imageKey"
        pictureAdapter.save(userId, promiseId, imageUrl, imageKey, pictureCommentType)
    }

    private fun handleWaitUser(pictureCommentType: PictureCommentType) {
        if (pictureCommentType !in listOf(WHAT_ARE_YOU_DOING, WHAT_TIME_IS_IT_NOW, DID_YOU_COME, I_LL_EAT_FIRST, WHERE_ARE_YOU)) {
            throw WaitUserInvalidCommentException.EXCEPTION
        }
    }

    private fun handleLateUser(pictureCommentType: PictureCommentType) {
        if (pictureCommentType !in listOf(RUNNING, GASPING, LEAVE_SOME, WAIT_A_BIT, SORRY_LATE)) {
            throw LateUserInvalidCommentException.EXCEPTION
        }
    }
}
