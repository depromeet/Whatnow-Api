package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.OauthUserInfoResponse
import com.depromeet.whatnow.api.auth.usecase.helper.KakaoOauthHelper
import com.depromeet.whatnow.api.auth.usecase.helper.OauthUserInfoDto
import org.springframework.stereotype.Service

@Service
class OauthUserInfoUseCase(
    val kakaoOauthHelper: KakaoOauthHelper,
) {
    fun execute(accessToken: String): OauthUserInfoResponse {
        val oauthUserInfo: OauthUserInfoDto = kakaoOauthHelper.getUserInfo(accessToken)
        return OauthUserInfoResponse.from(oauthUserInfo)
    }
}
