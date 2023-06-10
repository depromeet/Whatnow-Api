package com.depromeet.whatnow.domains.picture.service

import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.picture.adapter.PromiseAdapter
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.exception.CancelledUserUploadException
import com.depromeet.whatnow.domains.picture.exception.LateUserInvalidCommentException
import com.depromeet.whatnow.domains.picture.exception.UploadBeforeTrackingException
import com.depromeet.whatnow.domains.picture.exception.WaitUserInvalidCommentException
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PictureDomainService(
    val pictureAdapter: PromiseAdapter,
    val promiseUserAdapter: PromiseUserAdaptor,
) {
    @Transactional
    fun successUploadImage(userId: Long, promiseId: Long, imageKey: String, pictureCommentType: PictureCommentType) {
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)
        when (promiseUser.promiseUserType) {
            PromiseUserType.READY -> throw UploadBeforeTrackingException.EXCEPTION
            PromiseUserType.CANCEL -> throw CancelledUserUploadException.EXCEPTION
            PromiseUserType.WAIT -> handleWaitUser(pictureCommentType)
            PromiseUserType.LATE -> handleLateUser(pictureCommentType)
        }

        val imageUrl = IMAGE_DOMAIN + "promise/$promiseId/$imageKey"
        pictureAdapter.save(userId, promiseId, imageUrl, imageKey, pictureCommentType)
    }

    private fun handleWaitUser(pictureCommentType: PictureCommentType) {
        if (pictureCommentType !in listOf(
                PictureCommentType.WHAT_ARE_YOU_DOING,
                PictureCommentType.WHAT_TIME_IS_IT_NOW,
                PictureCommentType.DID_YOU_COME,
                PictureCommentType.I_LL_EAT_FIRST,
                PictureCommentType.WHERE_ARE_YOU,
            )
        ) {
            throw WaitUserInvalidCommentException.EXCEPTION
        }
    }

    private fun handleLateUser(pictureCommentType: PictureCommentType) {
        if (pictureCommentType !in listOf(
                PictureCommentType.RUNNING,
                PictureCommentType.GASPING,
                PictureCommentType.LEAVE_SOME,
                PictureCommentType.WAIT_A_BIT,
                PictureCommentType.SORRY_LATE,
            )
        ) {
            throw LateUserInvalidCommentException.EXCEPTION
        }
    }
}
