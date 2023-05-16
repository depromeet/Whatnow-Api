package com.depromeet.whatnow.config.security

import com.depromeet.whatnow.config.jwt.AccessTokenInfo
import com.depromeet.whatnow.config.jwt.JwtTokenHelper
import com.depromeet.whatnow.config.static.AUTH_HEADER
import com.depromeet.whatnow.config.static.BEARER
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    val jwtTokenProvider: JwtTokenHelper,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val token = resolveToken(request)
        token?. run {
            val authentication = getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val rawHeader = request.getHeader(AUTH_HEADER) ?: return null
        return if (rawHeader.length > BEARER.length && rawHeader.startsWith(BEARER)) {
            rawHeader.substring(BEARER.length)
        } else {
            null
        }
    }

    fun getAuthentication(token: String): Authentication {
        val accessTokenInfo: AccessTokenInfo = jwtTokenProvider.parseAccessToken(token)
        val userDetails: UserDetails =
            AuthDetails(accessTokenInfo.userId.toString(), accessTokenInfo.role)
        return UsernamePasswordAuthenticationToken(
            userDetails,
            "user",
            userDetails.authorities,
        )
    }
}
