package com.depromeet.whatnow.api.auth.controller

import com.depromeet.whatnow.api.auth.dto.request.RegisterRequest
import com.depromeet.whatnow.api.auth.dto.response.AbleRegisterResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthLoginLinkResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthTokenResponse
import com.depromeet.whatnow.api.auth.dto.response.OauthUserInfoResponse
import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import com.depromeet.whatnow.api.auth.usecase.OauthUserInfoUseCase
import com.depromeet.whatnow.api.auth.usecase.RegisterUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    val registerUseCase: RegisterUserUseCase,
    val oauthUserInfoUseCase: OauthUserInfoUseCase,
) {

    //    @Operation(summary = "개발용 회원가입입니다 ( 백엔드용 )", deprecated = true)
    @GetMapping("/oauth/kakao/develop")
    fun developUserSign(@RequestParam("code") code: String): TokenAndUserResponse {
        return registerUseCase.upsertKakaoOauthUser(code)
    }

//    @Operation(summary = "kakao oauth 링크발급 ( 백엔드용 )", description = "kakao 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/kakao/link/test")
    fun getKakaoOauthLink(): OauthLoginLinkResponse {
        return registerUseCase.getKaKaoOauthLink()
    }

//    @Operation(summary = "카카오 code 요청받는 곳입니다. ( 백엔드 용 ) ")
    @GetMapping("/oauth/kakao")
    fun getCredentialFromKaKao(
        @RequestParam("code") code: String,
    ): OauthTokenResponse {
        return registerUseCase.getCredentialFromKaKao(code)
    }

    //    @Operation(summary = "id_token 으로 회원가입을 합니다.")
    @PostMapping("/oauth/kakao/register")
    fun kakaoAuthCheckRegisterValid(
        @RequestParam("id_token") token: String,
        @Valid @RequestBody
        registerRequest: RegisterRequest,
    ): TokenAndUserResponse {
        return registerUseCase.registerUserByOCIDToken(token, registerRequest)
    }

    //    @Operation(summary = "회원가입이 가능한지 id token 으로 확인합니다.")
    @GetMapping("/oauth/kakao/register/valid")
    fun kakaoAuthCheckRegisterValid(
        @RequestParam("id_token") token: String,
    ): AbleRegisterResponse {
        return registerUseCase.checkAvailableRegister(token)
    }

//    @Operation(summary = "accessToken 으로 oauth user 정보를 가져옵니다.")
    @PostMapping("/oauth/kakao/info")
    fun kakaoOauthUserInfo(
        @RequestParam("access_token") accessToken: String,
    ): OauthUserInfoResponse {
        return oauthUserInfoUseCase.execute(accessToken)
    }
}
