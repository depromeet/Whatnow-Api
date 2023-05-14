package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.domains.user.adapter.RefreshTokenAdapter
import org.springframework.stereotype.Service

@Service
class LogoutUseCase(
    val refreshTokenAdapter: RefreshTokenAdapter,
) {
    fun execute() {
        TODO("시큐리티 컨텍스트에서 유저 정보 조회후 로그아웃 처리 예정")
//      val currentUserId: Long = SecurityUtils.getCurrentUserId()
//      refreshTokenAdapter.deleteByUserId(currentUserId)
    }
}
