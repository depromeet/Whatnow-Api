package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.image.dto.ImageDto
import com.depromeet.whatnow.config.s3.ImageFileExtension
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
        fileExtension: ImageFileExtension,
        promiseImageCommentType: PromiseImageCommentType,
    ): ImageDto {
        val currentUserId: Long = SecurityUtils.currentUserId
        val imageUrl = imageDomainService.promiseImageUploadSuccess(
            currentUserId,
            promiseId,
            imageKey,
            fileExtension,
            promiseImageCommentType,
        )
        return ImageDto(imageUrl)
    }

    fun userUploadImageSuccess(imageKey: String, fileExtension: ImageFileExtension): ImageDto {
        val currentUserId: Long = SecurityUtils.currentUserId
        val imageUrl = imageDomainService.userImageUploadSuccess(currentUserId, imageKey, fileExtension)
        return ImageDto(imageUrl)
    }
}
