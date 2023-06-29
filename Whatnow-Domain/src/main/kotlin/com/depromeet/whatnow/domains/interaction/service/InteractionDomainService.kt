package com.depromeet.whatnow.domains.interaction.service

import com.depromeet.whatnow.common.aop.lock.RedissonLock
import com.depromeet.whatnow.common.aop.verify.CheckUserParticipation
import com.depromeet.whatnow.domains.interaction.adapter.InteractionAdapter
import com.depromeet.whatnow.domains.interaction.domain.Interaction
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InteractionDomainService(
    val interactionAdapter: InteractionAdapter,
) {
    fun initInteraction(promiseId: Long, userId: Long) {
        InteractionType.values().forEach { interactionType ->
            interactionAdapter.save(Interaction.of(promiseId, userId, interactionType))
        }
    }

    @RedissonLock(
        lockName = "인터렉션",
        identifier = "userId",
    )
    @Transactional
    fun increment(promiseId: Long, userId: Long, interactionType: InteractionType) {
        val interaction = interactionAdapter.queryInteraction(promiseId, userId, interactionType)
        interaction.increment()
    }

    @CheckUserParticipation
    fun queryAllInteraction(promiseId: Long, userId: Long): List<Interaction> {
        return interactionAdapter.queryAllInteraction(promiseId, userId)
    }
}
