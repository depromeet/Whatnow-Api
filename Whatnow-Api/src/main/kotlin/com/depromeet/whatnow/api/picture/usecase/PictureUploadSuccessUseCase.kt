package com.depromeet.whatnow.api.picture.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.service.PictureDomainService

@UseCase
class PictureUploadSuccessUseCase(
    val pictureDomainService: PictureDomainService,
) {
    fun promiseUploadImageSuccess(promiseId: Long, imageKey: String, pictureCommentType: PictureCommentType) {
        val currentUserId: Long = SecurityUtils.currentUserId
        pictureDomainService.promiseUploadImageSuccess(currentUserId, promiseId, imageKey, pictureCommentType)
    }

    fun userUploadImageSuccess(imageKey: String) {
        val currentUserId: Long = SecurityUtils.currentUserId
        pictureDomainService.userUploadImageSuccess(currentUserId, imageKey)
    }
}
