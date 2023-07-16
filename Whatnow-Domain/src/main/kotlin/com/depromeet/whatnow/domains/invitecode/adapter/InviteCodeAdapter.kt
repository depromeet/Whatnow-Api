package com.depromeet.whatnow.domains.invitecode.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import com.depromeet.whatnow.domains.invitecode.repository.InviteCodeRepository

@Adapter
class InviteCodeAdapter(
    val inviteCodeRepository: InviteCodeRepository,
) {
    fun save(inviteCodeRedisEntity: InviteCodeRedisEntity): InviteCodeRedisEntity {
        return inviteCodeRepository.save(inviteCodeRedisEntity)
    }
}
