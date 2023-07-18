package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.domain.StartSharingNotification
import java.time.LocalDateTime

class StartSharingNotificationResponse(
    val promiseId: Long,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.START_SHARING, createdAt) {
    companion object {
        fun from(notification: StartSharingNotification): StartSharingNotificationResponse {
            return StartSharingNotificationResponse(
                notification.promiseId,
                notification.createdAt,
            )
        }
    }
}
