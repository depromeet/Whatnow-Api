package com.depromeet.whatnow.config.jwt

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
    } catch (e: io.jsonwebtoken.security.SecurityException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: MalformedJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: ExpiredJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: UnsupportedJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: IllegalArgumentException) {
        throw Exception("에러정책이후 바꿀예정")
    }
}

fun JwsParseExecuter(operation: () -> Jws<Claims>): Jws<Claims> {
    return try {
        operation()
    } catch (e: io.jsonwebtoken.security.SecurityException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: MalformedJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: ExpiredJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: UnsupportedJwtException) {
        throw Exception("에러정책이후 바꿀예정")
    } catch (e: IllegalArgumentException) {
        throw Exception("에러정책이후 바꿀예정")
    }
}
