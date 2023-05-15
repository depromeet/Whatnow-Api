package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.config.jwt.JwtTokenHelper
import com.depromeet.whatnow.domains.user.adapter.RefreshTokenAdapter
import com.depromeet.whatnow.domains.user.domain.RefreshTokenRedisEntity
import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.stereotype.Component

@Component
class TokenGenerateHelper(
    val jwtTokenHelper: JwtTokenHelper,
    val refreshTokenAdapter: RefreshTokenAdapter,
) {

    /**
     * jwt 토큰을 생성합니다.
     * 레디스에 리프레쉬 토큰을 ttl 값과 함께 저장합니다.
     */
    fun execute(user: User): TokenAndUserResponse {
        val newAccessToken: String = jwtTokenHelper.generateAccessToken(
            user.id!!,
            user.accountRole.toString(),
        )

        val newRefreshToken: String = jwtTokenHelper.generateRefreshToken(user.id!!)
        val newRefreshTokenEntity = RefreshTokenRedisEntity(user.id!!, newRefreshToken, jwtTokenHelper.refreshExpireIn)
        refreshTokenAdapter.save(newRefreshTokenEntity)

        return TokenAndUserResponse(newAccessToken, newRefreshToken, user, jwtTokenHelper.accessExpireIn, jwtTokenHelper.refreshExpireIn)
    }
}
