package com.depromeet.whatnow.domains.promiseprogress.repository

import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgress
import org.springframework.data.jpa.repository.JpaRepository

interface PromiseProgressRepository : JpaRepository<PromiseProgress, Long> {
    fun findByCode(code: String): PromiseProgress?
}
