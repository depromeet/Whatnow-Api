package com.depromeet.whatnow.domains.progresshistory.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import com.depromeet.whatnow.domains.progresshistory.exception.PromiseHistoryNotFoundException
import com.depromeet.whatnow.domains.progresshistory.repository.ProgressHistoryRepository

@Adapter
class ProgressHistoryAdapter(
    val promiseHistoryRepository: ProgressHistoryRepository,
) {
    fun save(progressHistory: ProgressHistory) {
        promiseHistoryRepository.save(progressHistory)
    }

    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): ProgressHistory {
        return promiseHistoryRepository.findByPromiseIdAndUserId(promiseId, userId)
            ?: run { throw PromiseHistoryNotFoundException.EXCEPTION }
    }
    fun delete(progressHistory: ProgressHistory) {
        promiseHistoryRepository.delete(progressHistory)
    }

    fun findByPromiseId(promiseId: Long): List<ProgressHistory> {
        return promiseHistoryRepository.findByPromiseId(promiseId)
    }

//    fun findAll(): List<PromiseHistory> {
//        return promiseHistoryRepository.findAll()
//    }
}
