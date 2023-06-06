package com.depromeet.whatnow.api.picture.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.picture.service.PictureDomainService

@UseCase
class PictureUploadSuccessUseCase(
    val pictureDomainService: PictureDomainService,
) {
    fun successUploadImage(promiseId: Long, imageKey: String) {
        val currentUserId: Long = SecurityUtils.currentUserId
        pictureDomainService.successUploadImage(currentUserId, promiseId, imageKey)
    }
}
