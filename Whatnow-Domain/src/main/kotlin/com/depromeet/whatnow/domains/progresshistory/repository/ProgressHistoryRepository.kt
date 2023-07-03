package com.depromeet.whatnow.domains.progresshistory.repository

import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProgressHistoryRepository : JpaRepository<ProgressHistory, Long> {
    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): ProgressHistory?

    @Query("select ph from ProgressHistory ph where ph.promiseId = :promiseId and ph.userId in :userIds")
    fun findByPromiseIdAndUserIdIn(promiseId: Long, userIds: List<Long>): List<ProgressHistory>
    fun findByPromiseId(promiseId: Long): List<ProgressHistory>
}
