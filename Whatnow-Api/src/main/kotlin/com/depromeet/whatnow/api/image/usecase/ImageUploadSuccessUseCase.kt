package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.image.service.ImageDomainService

@UseCase
class ImageUploadSuccessUseCase(
    val imageDomainService: ImageDomainService,
) {
    fun promiseUploadImageSuccess(
        promiseId: Long,
        imageKey: String,
        promiseImageCommentType: PromiseImageCommentType,
        userLocation: CoordinateVo,
    ) {
        val currentUserId: Long = SecurityUtils.currentUserId
        imageDomainService.promiseImageUploadSuccess(currentUserId, promiseId, imageKey, promiseImageCommentType, userLocation)
    }

    fun userUploadImageSuccess(imageKey: String) {
        val currentUserId: Long = SecurityUtils.currentUserId
        imageDomainService.userImageUploadSuccess(currentUserId, imageKey)
    }
}
