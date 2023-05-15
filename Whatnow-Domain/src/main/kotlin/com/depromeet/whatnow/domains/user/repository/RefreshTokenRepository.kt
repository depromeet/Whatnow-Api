package com.depromeet.whatnow.domains.user.repository

import com.depromeet.whatnow.domains.user.domain.RefreshTokenRedisEntity
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshTokenRedisEntity, String> {

    fun findByRefreshToken(refreshToken: String): RefreshTokenRedisEntity?
}
