package com.depromeet.whatnow.domains.invitecode.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "inviteCode")
class InviteCodeRedisEntity(
    @Id
    var promiseId: Long,

    @Indexed
    var inviteCode: String,

    @TimeToLive // TTL
    var ttl: Long,
)
