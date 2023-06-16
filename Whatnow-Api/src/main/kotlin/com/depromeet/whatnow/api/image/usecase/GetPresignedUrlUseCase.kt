package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.config.s3.S3UploadPresignedUrlService
import com.depromeet.whatnow.config.security.SecurityUtils

@UseCase
class GetPresignedUrlUseCase(
    val presignedUrlService: S3UploadPresignedUrlService,
) {
    fun forPromise(promiseId: Long, fileExtension: ImageFileExtension): ImageUrlResponse {
        return ImageUrlResponse.from(presignedUrlService.forPromise(promiseId, fileExtension))
    }

    fun forUser(fileExtension: ImageFileExtension): ImageUrlResponse {
        val currentUserId = SecurityUtils.currentUserId
        return ImageUrlResponse.from(presignedUrlService.forUser(currentUserId, fileExtension))
    }
}
