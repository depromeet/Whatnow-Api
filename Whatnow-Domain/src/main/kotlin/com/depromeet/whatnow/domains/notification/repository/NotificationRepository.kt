package com.depromeet.whatnow.domains.notification.repository

import com.depromeet.whatnow.domains.notification.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long>
