package com.depromeet.whatnow.config.jwt

data class AccessTokenInfo(
    val userId: Long,
    val role: String,
)
