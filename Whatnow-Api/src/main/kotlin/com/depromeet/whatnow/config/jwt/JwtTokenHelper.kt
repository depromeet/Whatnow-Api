package com.depromeet.whatnow.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.InvalidParameterException
import java.security.Key
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
        @Value("\${auth.jwt.secret-key}")
        private val jwtSecretKey: String,

        @Value("\${auth.jwt.access-exp}")
        private val accessExpireIn: Int,

        @Value("\${auth.jwt.refresh-exp}")
        private val refreshExpireIn: Int,
) {
    private fun getJws(token: String): Jws<Claims> {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        } catch (e: ExpiredJwtException) {
            throw Exception("에러정책이후 바꿀예정")
        } catch (e: Exception) {
            throw Exception("에러정책이후 바꿀예정")
        }
    }

    private val secretKey: Key
        get() = Keys.hmacShaKeyFor(jwtSecretKey.toByteArray(StandardCharsets.UTF_8))

    private fun buildAccessToken(
            id: Long, issuedAt: Date, accessTokenExpiresIn: Date, role: String): String {
        val encodedKey = secretKey
        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .claim(TOKEN_ROLE, role)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact()
    }

    private fun buildRefreshToken(id: Long, issuedAt: Date, accessTokenExpiresIn: Date): String {
        val encodedKey = secretKey
        return Jwts.builder()
                .setIssuer(TOKEN_ISSUER)
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact()
    }

    fun generateAccessToken(id: Long, role: String): String {
        val issuedAt = Date()
        val accessTokenExpiresIn: Date = Date(issuedAt.time + accessExpireIn * MILLI_TO_SECOND)
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role)
    }

    fun generateRefreshToken(id: Long): String {
        val issuedAt = Date()
        val refreshTokenExpiresIn: Date = Date(issuedAt.time + refreshExpireIn * MILLI_TO_SECOND)
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn)
    }

    fun isAccessToken(token: String): Boolean {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN)
    }

    fun isRefreshToken(token: String): Boolean {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(REFRESH_TOKEN)
    }

    fun parseAccessToken(token: String): AccessTokenInfo {
        if (isAccessToken(token)) {
            val claims: Claims = getJws(token).getBody()
            return AccessTokenInfo.builder()
                    .userId(claims.getSubject().toLong())
                    .role(claims.get(TOKEN_ROLE) as String)
                    .build()
        }
        throw Exception("에러정책이후 바꿀예정")
    }

    fun parseRefreshToken(token: String): Long {
        try {
            if (isRefreshToken(token)) {
                val claims: Claims = getJws(token).body
                return claims.subject.toLong()
            }
        } catch (e: Exception) {
            throw Exception("에러정책이후 바꿀예정")
        }
        throw Exception("에러정책이후 바꿀예정")
    }
}
