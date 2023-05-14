package com.depromeet.whatnow.config.jwt

import com.depromeet.whatnow.config.consts.KID
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.math.BigInteger
import java.security.Key
import java.security.KeyFactory
import java.security.spec.RSAPublicKeySpec
import java.util.*

@Component
class JwtOIDCHelper {

    fun getKidFromUnsignedTokenHeader(token: String, iss: String, aud: String): String {
        return getUnsignedTokenClaims(token, iss, aud).header[KID] as String
    }

    private fun getUnsignedTokenClaims(token: String, iss: String, aud: String): Jwt<Header<*>, Claims> {
        return JwtParseExecuter {
            Jwts.parserBuilder()
                .requireAudience(aud)
                .requireIssuer(iss)
                .build()
                .parseClaimsJwt(getUnsignedToken(token))
        }
    }

    private fun getUnsignedToken(token: String): String {
        val splitToken = token.split("\\.".toRegex())
        if (splitToken.size != 3) throw Exception("에러정책이후 바꿀예정")
        return splitToken[0] + "." + splitToken[1] + "."
    }

    fun getOIDCTokenJws(token: String, modulus: String, exponent: String): Jws<Claims> {
        return JwsParseExecuter {
            Jwts.parserBuilder()
                .setSigningKey(getRSAPublicKey(modulus, exponent))
                .build()
                .parseClaimsJws(token)
        }
    }

    fun getOIDCTokenBody(token: String, modulus: String, exponent: String): OIDCDecodePayload {
        val body = getOIDCTokenJws(token, modulus, exponent).body
        return OIDCDecodePayload(
            body.issuer,
            body.audience,
            body.subject,
            body["email", String::class.java],
        )
    }

    private fun getRSAPublicKey(modulus: String, exponent: String): Key {
        val keyFactory = KeyFactory.getInstance("RSA")
        val decodeN = Base64.getUrlDecoder().decode(modulus)
        val decodeE = Base64.getUrlDecoder().decode(exponent)
        val n = BigInteger(1, decodeN)
        val e = BigInteger(1, decodeE)
        val keySpec = RSAPublicKeySpec(n, e)
        return keyFactory.generatePublic(keySpec)
    }
}
