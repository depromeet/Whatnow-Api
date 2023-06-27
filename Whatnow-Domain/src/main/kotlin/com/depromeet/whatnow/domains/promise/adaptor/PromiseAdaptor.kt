package com.depromeet.whatnow.domains.promise.adaptor

import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promise.exception.PromiseNotFoundException
import com.depromeet.whatnow.domains.promise.repository.PromiseRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDateTime

// @Adaptor
@Component
class PromiseAdaptor(
    var promiseRepository: PromiseRepository,
) {
    fun queryPromise(promiseId: Long): Promise {
        return promiseRepository.findByIdOrNull(promiseId) ?: run { throw PromiseNotFoundException.EXCEPTION }
    }
    fun queryPromises(promiseIds: List<Long>): List<Promise> {
        return promiseRepository.findAllById(promiseIds)
    }
    fun queryPromiseByMainUserId(mainUserId: Long): List<Promise> {
        return promiseRepository.findAllByMainUserId(mainUserId)
    }
    fun queryPromiseByMainUserIdAfterNow(mainUserId: Long): List<Promise> {
        return promiseRepository.findAllByMainUserIdAfterNow(mainUserId, LocalDateTime.now())
    }
    fun save(promise: Promise): Promise {
        return promiseRepository.save(promise)
    }

    fun delete(promiseId: Long) {
        return promiseRepository.deleteById(promiseId)
    }

    fun queryPromisesWithStatus(userId: Long, promiseType: PromiseType): List<Promise> {
        return promiseRepository.findByUserIdAndStatus(userId, promiseType)
    }
}
