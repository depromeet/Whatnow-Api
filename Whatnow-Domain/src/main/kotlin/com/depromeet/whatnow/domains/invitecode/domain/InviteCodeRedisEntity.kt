package com.depromeet.whatnow.domains.invitecode.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.HashIndexed
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "inviteCode")
class InviteCodeRedisEntity(
    @Id
    var promiseId: Long,

    @HashIndexed
    var inviteCode: String,

    @TimeToLive // TTL
    var ttl: Long,
)
