package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.request.RegisterRequest
import com.depromeet.whatnow.api.auth.dto.response.AbleRegisterResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthLoginLinkResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthTokenResponse
import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService
import org.springframework.stereotype.Service

@Service // TODO : UseCase annotation 을 커스텀 하게 만들고싶은데 위치를 어디로 줘야할지? 커먼 레이어가없음
class RegisterUserUseCase(
    val userDomainService: UserDomainService,
) {
    fun execute(): User {
        return userDomainService.registerUser()
    }

    fun upsertKakaoOauthUser(code: String): TokenAndUserResponse {
        TODO("Not yet implemented")
    }

    fun getKaKaoOauthLink(): OauthLoginLinkResponse {
        TODO("Not yet implemented")
    }

    fun getCredentialFromKaKao(code: String): OauthTokenResponse {
        TODO("Not yet implemented")
    }

    fun registerUserByOCIDToken(token: String, registerRequest: RegisterRequest): TokenAndUserResponse {
        TODO("Not yet implemented")
    }

    fun checkAvailableRegister(token: String): AbleRegisterResponse {
        TODO("Not yet implemented")
    }
}
