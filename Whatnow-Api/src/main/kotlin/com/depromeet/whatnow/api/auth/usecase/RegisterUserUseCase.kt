package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.request.RegisterRequest
import com.depromeet.whatnow.api.auth.dto.response.AbleRegisterResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthLoginLinkResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthTokenResponse
import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.api.auth.usecase.helper.KakaoOauthHelper
import com.depromeet.whatnow.api.auth.usecase.helper.OauthUserInfoDto
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService
import org.springframework.stereotype.Service

@Service // TODO : UseCase annotation 을 커스텀 하게 만들고싶은데 위치를 어디로 줘야할지? 커먼 레이어가없음
class RegisterUserUseCase(
    val userDomainService: UserDomainService,
    val kakaoOauthHelper: KakaoOauthHelper,
) {
    fun execute(): User {
        return userDomainService.registerUser()
    }

    fun upsertKakaoOauthUser(code: String): TokenAndUserResponse {
        val oauthAccessToken: String = kakaoOauthHelper.getOauthToken(code).accessToken
        val oauthUserInfo: OauthUserInfoDto = kakaoOauthHelper.getUserInfo(oauthAccessToken)

//        val profile: Profile = oauthUserInfo.toProfile()
        val user: User = userDomainService.upsertUser(oauthUserInfo.username, oauthUserInfo.profileImage, oauthUserInfo.isDefaultImage, oauthUserInfo.toOauthInfo())
        return TokenAndUserResponse("나중에 넣을 예정(우리 백엔드 토큰)", "나중에 넣을 예정(우리 백엔드 토큰)", user)
    }

    fun getKaKaoOauthLink(): OauthLoginLinkResponse {
        return OauthLoginLinkResponse(kakaoOauthHelper.kaKaoOauthLink())
    }

    fun getCredentialFromKaKao(code: String): OauthTokenResponse {
        val tokens = kakaoOauthHelper.getOauthToken(code)
        return OauthTokenResponse(tokens.accessToken, tokens.refreshToken, tokens.idToken)
    }

    fun registerUserByOCIDToken(token: String, registerRequest: RegisterRequest): TokenAndUserResponse {
        TODO("Not yet implemented")
    }

    fun checkAvailableRegister(token: String): AbleRegisterResponse {
        TODO("Not yet implemented")
    }
}
