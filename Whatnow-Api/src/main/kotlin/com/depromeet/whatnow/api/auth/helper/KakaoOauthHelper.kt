package com.depromeet.whatnow.api.auth.helper

import com.depromeet.whatnow.api.KakaoInfoClient
import com.depromeet.whatnow.api.KakaoOauthClient
import com.depromeet.whatnow.api.dto.KakaoInformationResponse
import com.depromeet.whatnow.api.dto.KakaoTokenResponse
import com.depromeet.whatnow.api.dto.OIDCPublicKeysResponse
import com.depromeet.whatnow.api.dto.UnlinkKaKaoTarget
import com.depromeet.whatnow.config.OauthProperties
import com.depromeet.whatnow.config.jwt.OIDCDecodePayload
import com.depromeet.whatnow.config.static.BEARER
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import org.springframework.stereotype.Service

@Service
class KakaoOauthHelper(
    val oauthProperties: OauthProperties,
    val kakaoInfoClient: KakaoInfoClient,
    val kakaoOauthClient: KakaoOauthClient,
    val oauthOIDCHelper: OauthOIDCHelper,
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
        val response = kakaoInfoClient.kakaoUserInfo(BEARER + oauthAccessToken)
        val kakaoAccount: KakaoInformationResponse.KakaoAccount = response.kakaoAccount
        return OauthUserInfoDto(response.id, kakaoAccount.profile.profileImageUrl, kakaoAccount.profile.isDefaultImage, kakaoAccount.profile.nickname, kakaoAccount.email, OauthProvider.KAKAO)
    }

    fun getOIDCDecodePayload(token: String): OIDCDecodePayload {
        val oidcPublicKeysResponse: OIDCPublicKeysResponse = kakaoOauthClient.kakaoOIDCOpenKeys()
        return oauthOIDCHelper.getPayloadFromIdToken(
            token,
            kakaoOauth.baseUrl,
            kakaoOauth.appId,
            oidcPublicKeysResponse,
        )
    }

    fun getOauthInfoByIdToken(idToken: String): OauthInfo {
        val oidcDecodePayload: OIDCDecodePayload = getOIDCDecodePayload(idToken)
        return OauthInfo(oidcDecodePayload.sub, OauthProvider.KAKAO)
    }

    fun unlink(oid: String) {
        val kakaoAdminKey: String = oauthProperties.kakao.adminKey
        val unlinkKaKaoTarget = UnlinkKaKaoTarget(oid)
        val header = "KakaoAK $kakaoAdminKey"
        kakaoInfoClient.unlinkUser(header, unlinkKaKaoTarget)
    }
}
