package com.depromeet.whatnow.domains.invitecode.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import com.depromeet.whatnow.domains.invitecode.exception.InviteCodeNotFoundException
import com.depromeet.whatnow.domains.invitecode.repository.InviteCodeRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class InviteCodeAdapter(
    val inviteCodeRepository: InviteCodeRepository,
) {
    fun save(inviteCodeRedisEntity: InviteCodeRedisEntity): InviteCodeRedisEntity {
        return inviteCodeRepository.save(inviteCodeRedisEntity)
    }
    fun findByPromiseId(promiseId: Long): InviteCodeRedisEntity {
        return inviteCodeRepository.findByIdOrNull(promiseId) ?: throw InviteCodeNotFoundException.EXCEPTION
    }

    fun findByInviteCode(inviteCode: String): InviteCodeRedisEntity {
        return inviteCodeRepository.findByInviteCode(inviteCode) ?: throw InviteCodeNotFoundException.EXCEPTION
    }
}
