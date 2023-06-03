package com.depromeet.whatnow.domains.promiseprogress.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgress
import com.depromeet.whatnow.domains.promiseprogress.exception.PromiseProgressNotFoundException
import com.depromeet.whatnow.domains.promiseprogress.repository.PromiseProgressRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class PromiseProgressAdapter(
    val promiseProgressRepository: PromiseProgressRepository,
) {
    fun findAll(): List<PromiseProgress> {
        return promiseProgressRepository.findAll()
    }

    fun queryByCode(code: String): PromiseProgress {
        return promiseProgressRepository.findByCode(code)
            ?: run { throw PromiseProgressNotFoundException.EXCEPTION }
    }

    fun queryId(progressId: Long): PromiseProgress {
        return promiseProgressRepository.findByIdOrNull(progressId) ?: run {
            throw PromiseProgressNotFoundException.EXCEPTION
        }
    }

    fun findById(progressId: Long): PromiseProgress? {
        return promiseProgressRepository.findByIdOrNull(progressId)
    }
}
