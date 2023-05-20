package com.depromeet.whatnow.domains.promise.repository

import com.depromeet.whatnow.domains.promise.domain.Promise
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PromiseRepository : JpaRepository<Promise,Long> {
    fun findAllByMainUserId(mainUserId: Long): List<Promise>
    @Query("SELECT p FROM Promise p WHERE p.mainUserId = :mainUserId AND p.endTime > :now")
    fun findAllByMainUserIdAfterNow(mainUserId: Long, now: LocalDateTime): List<Promise>
}