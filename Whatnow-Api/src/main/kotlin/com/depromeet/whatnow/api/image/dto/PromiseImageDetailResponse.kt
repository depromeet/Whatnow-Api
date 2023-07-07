package com.depromeet.whatnow.api.image.dto

import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType

data class PromiseImageDetailResponse(
    val uploadUserId: Long,
    val promiseImageCommentType: PromiseImageCommentType,
) {
    companion object {
        fun from(promiseImage: PromiseImage): PromiseImageDetailResponse {
            return PromiseImageDetailResponse(promiseImage.userId, promiseImage.promiseImageCommentType)
        }
    }
}
