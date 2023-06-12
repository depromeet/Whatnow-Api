package com.depromeet.whatnow.domains.progresshistory.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import org.springframework.transaction.annotation.Transactional

@DomainService
class ProgressHistoryDomainService(
    val progressHistoryAdapter: ProgressHistoryAdapter,
) {

    fun initHistory(promiseId: Long, userId: Long) {
        progressHistoryAdapter.save(ProgressHistory.of(promiseId, userId))
    }

    fun deleteHistory(promiseId: Long, userId: Long) {
        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        progressHistoryAdapter.delete(history)
    }

    @Transactional
    fun change(promiseId: Long, userId: Long, progress: PromiseProgress): ProgressHistory {
        val history = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        history.change(progress)
        return history
    }
}
