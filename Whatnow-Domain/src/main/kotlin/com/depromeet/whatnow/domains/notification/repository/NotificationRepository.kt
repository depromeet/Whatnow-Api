package com.depromeet.whatnow.domains.notification.repository

import com.depromeet.whatnow.domains.notification.domain.Notification
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByTargetUserIdOrderByCreatedAtDesc(targetUserId: Long, pageable: Pageable): Slice<Notification>
}
