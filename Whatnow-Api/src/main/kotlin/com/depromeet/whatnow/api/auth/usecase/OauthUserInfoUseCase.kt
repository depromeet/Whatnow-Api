package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.OauthUserInfoResponse
import com.depromeet.whatnow.api.auth.helper.KakaoOauthHelper
import com.depromeet.whatnow.api.auth.helper.OauthUserInfoDto
import org.springframework.stereotype.Service

@Service
class OauthUserInfoUseCase(
    val kakaoOauthHelper: KakaoOauthHelper,
) {

    /**
     * 어세스토큰으로 해당 유저의 정보를 가져옵니다.
     */
    fun execute(accessToken: String): OauthUserInfoResponse {
        val oauthUserInfo: OauthUserInfoDto = kakaoOauthHelper.getUserInfo(accessToken)
        return OauthUserInfoResponse.from(oauthUserInfo)
    }
}
