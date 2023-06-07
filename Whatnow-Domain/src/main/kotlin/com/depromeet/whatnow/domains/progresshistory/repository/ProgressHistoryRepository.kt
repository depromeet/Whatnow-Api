package com.depromeet.whatnow.domains.progresshistory.repository

import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import org.springframework.data.jpa.repository.JpaRepository

interface ProgressHistoryRepository : JpaRepository<ProgressHistory, Long> {
    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): ProgressHistory?
}
