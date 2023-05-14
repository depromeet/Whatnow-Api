package com.depromeet.whatnow.api.auth.dto.response

data class OauthTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
)
