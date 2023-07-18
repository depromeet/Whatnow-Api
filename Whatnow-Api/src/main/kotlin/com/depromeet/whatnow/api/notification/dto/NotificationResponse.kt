package com.depromeet.whatnow.api.notification.dto

import java.time.LocalDate

data class NotificationResponse(
    val notifications: Map<LocalDate, List<NotificationAbstract>>,
)
