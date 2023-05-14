package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.api.KakaoInfoClient
import com.depromeet.whatnow.api.KakaoOauthClient
import com.depromeet.whatnow.api.dto.KakaoInformationResponse
import com.depromeet.whatnow.api.dto.KakaoTokenResponse
import com.depromeet.whatnow.config.OauthProperties
import com.depromeet.whatnow.config.static.BEARER
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import org.springframework.stereotype.Service

@Service
class KakaoOauthHelper(
    val oauthProperties: OauthProperties,
    val kakaoInfoClient: KakaoInfoClient,
    val kakaoOauthClient: KakaoOauthClient,
//    val oauthOIDCHelper: OauthOIDCHelper,
) {
    var kakaoOauth: OauthProperties.OAuthSecret = oauthProperties.kakao

    fun kaKaoOauthLink(): String {
        return kakaoOauth.baseUrl +
            "/oauth/authorize?client_id=${kakaoOauth.clientId}" +
            "&redirect_uri=${kakaoOauth.redirectUrl}" +
            "&response_type=code"
    }

    fun getOauthToken(code: String): KakaoTokenResponse {
        return kakaoOauthClient.kakaoAuth(
            kakaoOauth.clientId,
            kakaoOauth.redirectUrl,
            code,
            kakaoOauth.clientSecret,
        )
    }

    fun getUserInfo(oauthAccessToken: String): OauthUserInfoDto {
        val response: KakaoInformationResponse =
            kakaoInfoClient.kakaoUserInfo(BEARER + oauthAccessToken)
        return OauthUserInfoDto(response.id, response.kakaoAccount.profile.profileImageUrl, response.kakaoAccount.profile.isDefaultImage, response.kakaoAccount.profile.nickname, OauthProvider.KAKAO)
    }

//    fun getOIDCDecodePayload(token: String?): OIDCDecodePayload {
//        val oidcPublicKeysResponse: OIDCPublicKeysResponse = kakaoOauthClient.kakaoOIDCOpenKeys()
//        return oauthOIDCHelper.getPayloadFromIdToken(
//            token,
//            kakaoOauth.baseUrl,
//            kakaoOauth.appId,
//            oidcPublicKeysResponse
//        )
//    }
//
//    fun getOauthInfoByIdToken(idToken: String?): OauthInfo {
//        val oidcDecodePayload: OIDCDecodePayload = getOIDCDecodePayload(idToken)
//        return OauthInfo.builder()
//            .provider(OauthProvider.KAKAO)
//            .oid(oidcDecodePayload.getSub())
//            .build()
//    }
    // 회원탈퇴용 나중에 만들예정
//    fun unlink(oid: String?) {
//        val kakaoAdminKey: String = oauthProperties.getKakaoAdminKey()
//        val unlinkKaKaoTarget: UnlinkKaKaoTarget = UnlinkKaKaoTarget.from(oid)
//        val header: String = "KakaoAK $kakaoAdminKey"
//        kakaoInfoClient.unlinkUser(header, unlinkKaKaoTarget)
//    }
}
