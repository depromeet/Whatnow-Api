package com.depromeet.whatnow.config.jwt

import com.depromeet.whatnow.config.static.ACCESS_TOKEN
import com.depromeet.whatnow.config.static.MILLI_TO_SECOND
import com.depromeet.whatnow.config.static.REFRESH_TOKEN
import com.depromeet.whatnow.config.static.TOKEN_ISSUER
import com.depromeet.whatnow.config.static.TOKEN_ROLE
import com.depromeet.whatnow.config.static.TOKEN_TYPE
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Component
class JwtTokenHelper(
    @Value("\${auth.jwt.secret-key}")
    private val jwtSecretKey: String,

    @Value("\${auth.jwt.access-exp}")
    private val accessExpireIn: Int,

    @Value("\${auth.jwt.refresh-exp}")
    private val refreshExpireIn: Int,
) {
    fun getJws(token: String): Jws<Claims> {
        return JwsParseExecuter {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)
        }
    }

    private val secretKey: Key
        get() = Keys.hmacShaKeyFor(jwtSecretKey.toByteArray(StandardCharsets.UTF_8))

    private fun buildAccessToken(
        id: Long,
        issuedAt: Date,
        accessTokenExpiresIn: Date,
        role: String,
    ): String {
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
        val accessTokenExpiresIn = Date(issuedAt.time + accessExpireIn * MILLI_TO_SECOND)
        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role)
    }

    fun generateRefreshToken(id: Long): String {
        val issuedAt = Date()
        val refreshTokenExpiresIn = Date(issuedAt.time + refreshExpireIn * MILLI_TO_SECOND)
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn)
    }

    fun isAccessToken(token: String): Boolean {
        return getJws(token).body[TOKEN_TYPE]!! == ACCESS_TOKEN
    }

    fun isRefreshToken(token: String): Boolean {
        return getJws(token).body[TOKEN_TYPE]!! == REFRESH_TOKEN
    }

    fun parseAccessToken(token: String): AccessTokenInfo {
        if (isAccessToken(token)) {
            val claims: Claims = getJws(token).body
            return AccessTokenInfo(claims.subject.toLong(), claims[TOKEN_ROLE] as String)
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
