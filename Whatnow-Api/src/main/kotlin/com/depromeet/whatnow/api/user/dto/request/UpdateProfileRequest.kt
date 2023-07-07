package com.depromeet.whatnow.api.user.dto.request

data class UpdateProfileRequest(
    val username: String,
    val profileImage: String,
    val isDefaultImg: Boolean,
    val imageKey: String,
)
