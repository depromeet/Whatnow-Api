package com.depromeet.whatnow.api.image.dto

import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType

data class ImageCommentElement(
    val promiseUserType: PromiseUserType,
    val comments: List<PromiseImageCommentType>,
)
