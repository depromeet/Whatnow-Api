package com.depromeet.whatnow.api.user.dto.request

import com.depromeet.whatnow.config.s3.ImageFileExtension

data class UpdateProfileRequest(
    val username: String,
    val profileImage: String,
    val isDefaultImg: Boolean,
    val imageKey: String,
    val fileExtension: ImageFileExtension,
)
