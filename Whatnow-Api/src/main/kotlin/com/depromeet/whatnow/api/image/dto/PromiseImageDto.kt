package com.depromeet.whatnow.api.image.dto

import com.depromeet.whatnow.domains.image.domain.PromiseImage

data class PromiseImageDto(
    val imageKey: String,
    val imageUrl: String,
    val uploadUserId: Long,
) {
    companion object {
        fun from(promiseImage: PromiseImage): PromiseImageDto {
            return PromiseImageDto(promiseImage.imageKey, promiseImage.uri, promiseImage.userId)
        }
    }
}
