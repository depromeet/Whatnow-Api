package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
import com.depromeet.whatnow.domains.image.service.ImageDomainService

@UseCase
class ImageUploadSuccessUseCase(
    val imageDomainService: ImageDomainService,
) {
    fun promiseUploadImageSuccess(promiseId: Long, imageKey: String, imageCommentType: ImageCommentType) {
        val currentUserId: Long = SecurityUtils.currentUserId
        imageDomainService.promiseUploadImageSuccess(currentUserId, promiseId, imageKey, imageCommentType)
    }

    fun userUploadImageSuccess(imageKey: String) {
        val currentUserId: Long = SecurityUtils.currentUserId
        imageDomainService.userUploadImageSuccess(currentUserId, imageKey)
    }
}
