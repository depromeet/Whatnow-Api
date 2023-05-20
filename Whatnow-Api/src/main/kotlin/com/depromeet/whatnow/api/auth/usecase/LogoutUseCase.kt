package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.adapter.RefreshTokenAdapter
import org.springframework.stereotype.Service

@Service
class LogoutUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
) {
    fun execute() {
        val currentUserId: Long = SecurityUtils.currentUserId
        refreshTokenAdapter.deleteByUserId(currentUserId)
    }
}
