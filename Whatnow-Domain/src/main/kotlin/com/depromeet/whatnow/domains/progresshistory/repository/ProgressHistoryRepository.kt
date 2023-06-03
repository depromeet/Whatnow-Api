package com.depromeet.whatnow.domains.progresshistory.repository

import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgress
import org.springframework.data.jpa.repository.JpaRepository

interface ProgressHistoryRepository : JpaRepository<ProgressHistory, Long> {
}
