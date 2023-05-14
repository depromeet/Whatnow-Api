package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.api.dto.OIDCPublicKeyDto
import com.depromeet.whatnow.api.dto.OIDCPublicKeysResponse
import com.depromeet.whatnow.config.jwt.JwtOIDCHelper
import com.depromeet.whatnow.config.jwt.OIDCDecodePayload
import org.springframework.stereotype.Component

@Component
class OauthOIDCHelper(
    val jwtOIDCProvider: JwtOIDCHelper
) {
    private fun getKidFromUnsignedIdToken(token: String, iss: String, aud: String): String {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud)
    }

    fun getPayloadFromIdToken(
        token: String, iss: String, aud: String, oidcPublicKeysResponse: OIDCPublicKeysResponse
    ): OIDCDecodePayload {
        val kid = getKidFromUnsignedIdToken(token, iss, aud)
        val oidcPublicKeyDto: OIDCPublicKeyDto = oidcPublicKeysResponse.keys.stream()
            .filter { o -> o.kid == kid }
            .findFirst()
            .orElseThrow()
        return jwtOIDCProvider.getOIDCTokenBody(
            token, oidcPublicKeyDto.n, oidcPublicKeyDto.e
        )
    }
}