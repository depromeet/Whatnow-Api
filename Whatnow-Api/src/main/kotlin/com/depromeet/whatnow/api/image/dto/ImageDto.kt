package com.depromeet.whatnow.api.image.dto

data class ImageDto(
    val imageUrl: String,
) {
    companion object {
        fun from(imageUrl: String): ImageDto {
            return ImageDto(imageUrl)
        }
    }
}
