package com.depromeet.whatnow.domains.progresshistory.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.progresshistory.repository.ProgressHistoryRepository

@Adapter
class ProgressHistoryAdapter(
    val promiseHistoryRepository: ProgressHistoryRepository,
) {
//    fun findAll(): List<PromiseHistory> {
//        return promiseHistoryRepository.findAll()
//    }
}
