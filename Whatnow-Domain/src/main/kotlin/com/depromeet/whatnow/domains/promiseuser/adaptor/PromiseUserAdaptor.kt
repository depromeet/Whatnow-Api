package com.depromeet.whatnow.domains.promiseuser.adaptor

import com.depromeet.whatnow.domains.promise.exception.PromiseNotFoundException
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.repository.PromiseUserRepository
import org.springframework.data.repository.findByIdOrNull

//@Adaptor
class PromiseUserAdaptor(
    val promiseUserRepository: PromiseUserRepository
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
}