package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.config.jwt.JwtTokenHelper
import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TokenGenerateHelper(
    val jwtTokenHelper: JwtTokenHelper,
    val refreshTokenAdaptor: RefreshTokenAdaptor
) {

    @Transactional
    fun execute(user: User): TokenAndUserResponse {
        val newAccessToken: String = jwtTokenProvider.generateAccessToken(
            user.getId(), user.getAccountRole().getValue()
        )
        val newRefreshToken: String = jwtTokenProvider.generateRefreshToken(user.getId())
        val newRefreshTokenEntity: RefreshTokenEntity = RefreshTokenEntity.builder()
            .refreshToken(newRefreshToken)
            .id(user.getId())
            .ttl(jwtTokenProvider.getRefreshTokenTTlSecond())
            .build()
        refreshTokenAdaptor.save(newRefreshTokenEntity)
        return TokenAndUserResponse.builder()
            .userProfile(ProfileViewDto.from(user))
            .accessToken(newAccessToken)
            .accessTokenAge(jwtTokenProvider.getAccessTokenTTlSecond())
            .refreshTokenAge(jwtTokenProvider.getRefreshTokenTTlSecond())
            .refreshToken(newRefreshToken)
            .build()
    }
}