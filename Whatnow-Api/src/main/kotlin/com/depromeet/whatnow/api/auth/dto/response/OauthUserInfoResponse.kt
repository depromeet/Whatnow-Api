package com.depromeet.whatnow.api.auth.dto.response

data class OauthUserInfoResponse(
    val email: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
)
