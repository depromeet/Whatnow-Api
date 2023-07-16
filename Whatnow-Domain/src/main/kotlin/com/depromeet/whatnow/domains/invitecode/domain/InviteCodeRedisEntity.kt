package com.depromeet.whatnow.domains.invitecode.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "inviteCode")
class InviteCodeRedisEntity(
    @Id
    var key: String,

    @TimeToLive // TTL
    var ttl: Long,
)
