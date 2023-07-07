package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.config.s3.S3Service
import com.depromeet.whatnow.config.security.SecurityUtils

@UseCase
class GetPresignedUrlUseCase(
    val s3Service: S3Service,
) {
    fun forPromise(promiseId: Long, fileExtension: ImageFileExtension): ImageUrlResponse {
        return ImageUrlResponse.from(s3Service.getPresignedUrlForPromise(promiseId, fileExtension))
    }

    fun forUser(fileExtension: ImageFileExtension): ImageUrlResponse {
        val currentUserId = SecurityUtils.currentUserId
        return ImageUrlResponse.from(s3Service.getPresignedUrlForUser(currentUserId, fileExtension))
    }
}
