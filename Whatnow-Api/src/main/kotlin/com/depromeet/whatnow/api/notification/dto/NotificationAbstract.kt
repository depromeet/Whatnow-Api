package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.NotificationType
import java.time.LocalDateTime

abstract class NotificationAbstract(
    val notificationType: NotificationType,
    open val createdAt: LocalDateTime,
)
