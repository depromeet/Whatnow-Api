package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.EndSharingNotification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import java.time.LocalDateTime

class EndSharingNotificationResponse(
    val promiseId: Long,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.END_SHARING, createdAt) {
    companion object {
        fun from(notification: EndSharingNotification): EndSharingNotificationResponse {
            return EndSharingNotificationResponse(
                notification.promiseId,
                notification.createdAt,
            )
        }
    }
}
