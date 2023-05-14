package com.depromeet.whatnow.api.auth.dto.response

import com.depromeet.whatnow.domains.user.domain.User

data class TokenAndUserResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User, // 나중에 dto로 바꿀게용~
    val accessTokenExpireIn: Long,
    val refreshTokenExpireIn: Long,
)
