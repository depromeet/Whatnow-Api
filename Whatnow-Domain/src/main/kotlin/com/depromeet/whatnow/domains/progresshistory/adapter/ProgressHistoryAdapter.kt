package com.depromeet.whatnow.domains.progresshistory.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.progresshistory.repository.ProgressHistoryRepository
import com.depromeet.whatnow.domains.promisehistory.domain.PromiseHistory
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgress
import com.depromeet.whatnow.domains.promiseprogress.exception.PromiseProgressNotFoundException
import com.depromeet.whatnow.domains.promiseprogress.repository.PromiseProgressRepository

@Adapter
class ProgressHistoryAdapter(
    val promiseHistoryRepository : ProgressHistoryRepository,
) {
//    fun findAll(): List<PromiseHistory> {
//        return promiseHistoryRepository.findAll()
//    }

}
