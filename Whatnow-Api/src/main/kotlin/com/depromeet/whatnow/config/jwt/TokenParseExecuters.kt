package com.depromeet.whatnow.config.jwt

import com.depromeet.whatnow.exception.custom.ExpiredTokenException
import com.depromeet.whatnow.exception.custom.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException

fun JwtParseExecuter(operation: () -> Jwt<Header<*>, Claims>): Jwt<Header<*>, Claims> {
    return try {
        operation()
    } catch (ex: Exception) {
        when (ex) {
            is io.jsonwebtoken.security.SecurityException,
            is MalformedJwtException,
            is UnsupportedJwtException,
            is IllegalArgumentException -> throw InvalidTokenException.EXCEPTION
            is ExpiredJwtException -> throw ExpiredTokenException.EXCEPTION
            else -> throw ex
        }
    }
}

fun JwsParseExecuter(operation: () -> Jws<Claims>): Jws<Claims> {
    return try {
        operation()
    } catch (ex: Exception) {
        when (ex) {
            is io.jsonwebtoken.security.SecurityException,
            is MalformedJwtException,
            is UnsupportedJwtException,
            is IllegalArgumentException -> throw InvalidTokenException.EXCEPTION
            is ExpiredJwtException -> throw ExpiredTokenException.EXCEPTION
            else -> throw ex
        }
    }
}
