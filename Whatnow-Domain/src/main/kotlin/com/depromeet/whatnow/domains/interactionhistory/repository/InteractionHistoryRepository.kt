package com.depromeet.whatnow.domains.interactionhistory.repository

import com.depromeet.whatnow.domains.interactionhistory.domain.InteractionHistory
import org.springframework.data.jpa.repository.JpaRepository

interface InteractionHistoryRepository : JpaRepository<InteractionHistory, Long>
