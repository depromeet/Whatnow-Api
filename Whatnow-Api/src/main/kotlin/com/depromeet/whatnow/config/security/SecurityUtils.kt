package com.depromeet.whatnow.config.security

import com.depromeet.whatnow.exception.custom.SecurityContextNotFoundException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.CollectionUtils

class SecurityUtils {
    companion object {
        private val anonymous = SimpleGrantedAuthority("ROLE_ANONYMOUS")
        private val swagger = SimpleGrantedAuthority("ROLE_SWAGGER")
        private val notUserAuthority = listOf(anonymous, swagger)

        val currentUserId: Long
            get() {
                val authentication = SecurityContextHolder.getContext().authentication
                    ?: throw SecurityContextNotFoundException.EXCEPTION
                return if (authentication.isAuthenticated && !CollectionUtils.containsAny(authentication.authorities, notUserAuthority)) {
                    authentication.name.toLong()
                } else {
                    0L
                }
                // 스웨거 유저일시 익명 유저 취급
                // 익명유저시 userId 0 반환
            }
    }
    // 스웨거 유저일시 익명 유저 취급
    // 익명유저시 userId 0 반환
}
