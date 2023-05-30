package com.depromeet.whatnow.api.image.dto

import com.depromeet.whatnow.config.s3.ImageUrlDto

class ImageUrlResponse(
    val presignedUrl: String,
    val key: String,
) {
    companion object {
        fun from(imageUrlDto: ImageUrlDto): ImageUrlResponse {
            return ImageUrlResponse(
                presignedUrl = imageUrlDto.url,
                key = imageUrlDto.key,
            )
        }
    }
}
