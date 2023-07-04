package com.depromeet.whatnow.domains.promiseactive.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "promiseActive")
class PromiseActiveRedisEntity(
    @Id
    var key: String,

    @TimeToLive // TTL
    var ttl: Long,
)
