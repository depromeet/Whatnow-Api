package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.config.jwt.JwtTokenHelper
import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TokenGenerateHelper(
    val jwtTokenHelper: JwtTokenHelper,
    val refreshTokenAdaptor: RefreshTokenAdaptor,
) {

    @Transactional
    fun execute(user: User): TokenAndUserResponse {
        val newAccessToken: String = jwtTokenHelper.generateAccessToken(
            user.id!!,
            user.accountRole.toString(),
        )
        val newRefreshToken: String = jwtTokenHelper.generateRefreshToken(user.id!!)
        val newRefreshTokenEntity: RefreshTokenEntity = RefreshTokenEntity.builder()
            .refreshToken(newRefreshToken)
            .id(user.get)
            .ttl(jwtTokenHelper.refreshExpireIn)
            .build()
        refreshTokenAdaptor.save(newRefreshTokenEntity)
        return TokenAndUserResponse
    }
}
