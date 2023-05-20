package com.depromeet.whatnow.api.auth.helper

import com.depromeet.whatnow.annotation.Helper
import com.depromeet.whatnow.api.dto.OIDCPublicKeyDto
import com.depromeet.whatnow.api.dto.OIDCPublicKeysResponse
import com.depromeet.whatnow.config.jwt.JwtOIDCHelper
import com.depromeet.whatnow.config.jwt.OIDCDecodePayload

@Helper
class OauthOIDCHelper(
    val jwtOIDCProvider: JwtOIDCHelper,
) {

    /**
     * idToken 의 공개키 검증과 함께
     * 토큰안에 들어있는 payload 의 정보를 가져옵니다.
     */
    fun getPayloadFromIdToken(
        token: String, // id Token
        iss: String, // 발행자
        aud: String, // 유저의 고유 oauth id
        oidcPublicKeysResponse: OIDCPublicKeysResponse,
    ): OIDCDecodePayload {
        val kid = getKidFromUnsignedIdToken(token, iss, aud)
        val oidcPublicKeyDto: OIDCPublicKeyDto = oidcPublicKeysResponse.keys.stream()
            .filter { o -> o.kid == kid }
            .findFirst()
            .orElseThrow()
        return jwtOIDCProvider.getOIDCTokenBody(
            token,
            oidcPublicKeyDto.n,
            oidcPublicKeyDto.e,
        )
    }

    private fun getKidFromUnsignedIdToken(token: String, iss: String, aud: String): String {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud)
    }
}
