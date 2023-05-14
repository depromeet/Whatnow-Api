package com.depromeet.whatnow.domains.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refreshToken")
class RefreshTokenRedisEntity(
    @Id
    var id: Long,

    @Indexed
    val refreshToken: String,

    @TimeToLive // TTL
    val ttl: Long,
)
