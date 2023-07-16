package com.depromeet.whatnow.domains.promiseactive.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.invitecode.domain.PromiseActiveRedisEntity
import com.depromeet.whatnow.domains.promiseactive.repository.PromiseActiveRepository

@Adapter
class PromiseActiveAdapter(
    val promiseActiveRepository: PromiseActiveRepository,
) {
    fun save(promiseActiveRedisEntity: PromiseActiveRedisEntity): PromiseActiveRedisEntity {
        return promiseActiveRepository.save(promiseActiveRedisEntity)
    }
}
