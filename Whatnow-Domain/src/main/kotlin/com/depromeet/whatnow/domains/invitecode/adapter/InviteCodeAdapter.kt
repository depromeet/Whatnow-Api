package com.depromeet.whatnow.domains.invitecode.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import com.depromeet.whatnow.domains.promiseactive.repository.PromiseActiveRepository

@Adapter
class InviteCodeAdapter(
    val promiseActiveRepository: PromiseActiveRepository,
) {
    fun save(promiseActiveRedisEntity: InviteCodeRedisEntity): InviteCodeRedisEntity {
        return promiseActiveRepository.save(promiseActiveRedisEntity)
    }
}
