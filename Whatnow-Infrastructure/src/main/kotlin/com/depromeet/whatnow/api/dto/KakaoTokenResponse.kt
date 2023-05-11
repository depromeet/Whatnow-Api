package com.depromeet.whatnow.api.dto

data class KakaoTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
)
