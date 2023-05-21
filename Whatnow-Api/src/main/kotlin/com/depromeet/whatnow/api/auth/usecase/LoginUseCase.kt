package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.api.auth.helper.KakaoOauthHelper
import com.depromeet.whatnow.api.auth.usecase.helper.TokenGenerateHelper
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService

@UseCase
class LoginUseCase(
    val kakaoOauthHelper: KakaoOauthHelper,
    val userDomainService: UserDomainService,
    val tokenGenerateHelper: TokenGenerateHelper,
) {
    fun execute(idToken: String): TokenAndUserResponse {
        val oauthInfo: OauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken)
        val user: User = userDomainService.loginUser(oauthInfo)
        return tokenGenerateHelper.execute(user)
    }
}
