package com.depromeet.whatnow.domains.promiseuser.adaptor

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.promise.exception.PromiseNotFoundException
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.exception.PromiseUserNotFoundException
import com.depromeet.whatnow.domains.promiseuser.repository.PromiseUserRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class PromiseUserAdaptor(
    val promiseUserRepository: PromiseUserRepository,
) {
    fun queryPromise(promiseId: Long): PromiseUser {
        return promiseUserRepository.findByIdOrNull(promiseId) ?: run { throw PromiseNotFoundException.EXCEPTION }
    }

    fun save(promiseUser: PromiseUser): PromiseUser {
        return promiseUserRepository.save(promiseUser)
    }

    fun delete(promiseId: Long) {
        return promiseUserRepository.deleteById(promiseId)
    }

    fun findByPromiseId(promiseId: Long): List<PromiseUser> {
        return promiseUserRepository.findByPromiseId(promiseId)
    }
    fun findByUserId(userId: Long): List<PromiseUser> {
        return promiseUserRepository.findByUserId(userId)
    }

    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): PromiseUser {
        return promiseUserRepository.findByPromiseIdAndUserId(promiseId, userId) ?: run { throw PromiseUserNotFoundException.EXCEPTION }
    }

    fun findByPromiseIds(promiseIds: List<Long>): List<PromiseUser> {
        return promiseUserRepository.findByPromiseIdIn(promiseIds)
    }

    fun findByUniquePromiseIds(promiseIds: List<Long>): Set<PromiseUser> {
        return promiseUserRepository.findByUniquePromiseIdIn(promiseIds)
    }
}
