package com.depromeet.whatnow.domains.user.adapter

import com.depromeet.whatnow.domains.user.domain.RefreshTokenRedisEntity
import com.depromeet.whatnow.domains.user.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import java.lang.Error

@Service
class RefreshTokenAdapter(
    val refreshTokenRepository: RefreshTokenRepository,
) {

    fun queryRefreshToken(refreshToken: String): RefreshTokenRedisEntity {
        return refreshTokenRepository
            .findByRefreshToken(refreshToken) ?: run { throw Error("엔티티 못찾음 에러바꾸기 나중에") }
    }

    fun save(refreshToken: RefreshTokenRedisEntity): RefreshTokenRedisEntity {
        return refreshTokenRepository.save(refreshToken)
    }

    fun deleteByUserId(userId: Long) {
        refreshTokenRepository.deleteById(userId.toString())
    }
}
