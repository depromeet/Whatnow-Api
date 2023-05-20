package com.depromeet.whatnow.domains.user.adapter

import com.depromeet.whatnow.domains.user.domain.RefreshTokenRedisEntity
import com.depromeet.whatnow.domains.user.repository.RefreshTokenRepository
import com.depromeet.whatnow.exception.custom.RefreshTokenExpiredException
import org.springframework.stereotype.Service

@Service
class RefreshTokenAdapter(
    val refreshTokenRepository: RefreshTokenRepository,
) {

    fun queryRefreshToken(refreshToken: String): RefreshTokenRedisEntity {
        return refreshTokenRepository
            .findByRefreshToken(refreshToken) ?: run { throw RefreshTokenExpiredException.EXCEPTION }
    }

    fun save(refreshToken: RefreshTokenRedisEntity): RefreshTokenRedisEntity {
        return refreshTokenRepository.save(refreshToken)
    }

    fun deleteByUserId(userId: Long) {
        refreshTokenRepository.deleteById(userId.toString())
    }
}
