package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.api.auth.usecase.helper.TokenGenerateHelper
import com.depromeet.whatnow.config.jwt.JwtTokenHelper
import com.depromeet.whatnow.domains.user.adapter.RefreshTokenAdapter
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.domain.RefreshTokenRedisEntity
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService
import org.springframework.stereotype.Service

@Service
class RefreshUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
    val jwtTokenHelper: JwtTokenHelper,
    val userAdapter: UserAdapter,
    val userDomainService: UserDomainService,
    val tokenGenerateHelper: TokenGenerateHelper
) {

    /**
     * 토큰 리프레쉬시 실행됩니다.
     * 리프레쉬토큰도 다시 새로 뽑아서 내려줍니다.
     */
    fun execute(refreshToken: String): TokenAndUserResponse {
        refreshTokenAdapter.queryRefreshToken(refreshToken)
        val refreshUserId: Long =
            jwtTokenHelper.parseRefreshToken(refreshToken)
        val user: User = userAdapter.queryUser(refreshUserId)
        // 리프레쉬 시에도 last로그인 정보 업데이트
        userDomainService.loginUser(user.oauthInfo)
        return tokenGenerateHelper.execute(user) // 리프레쉬 토큰도 새로고침해서 내려줍니다.
    }
}
