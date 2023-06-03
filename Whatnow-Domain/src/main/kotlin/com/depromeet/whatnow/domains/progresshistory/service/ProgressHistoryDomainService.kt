package com.depromeet.whatnow.domains.progresshistory.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter

@DomainService
class ProgressHistoryDomainService(
    val progressHistoryAdapter: ProgressHistoryAdapter,
) {

    fun initHistory(promiseId: Long, userId: Long) {
    }

    fun deleteHistory(promiseId: Long, userId: Long) {
    }
}
