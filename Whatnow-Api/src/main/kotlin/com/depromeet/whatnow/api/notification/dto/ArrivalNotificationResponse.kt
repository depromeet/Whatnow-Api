package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.ArrivalNotification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import java.time.LocalDateTime

class ArrivalNotificationResponse(
    val promiseId: Long,
    val senderUserId: Long,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.ARRIVAL, createdAt) {
    companion object {
        fun from(notification: ArrivalNotification): ArrivalNotificationResponse {
            return ArrivalNotificationResponse(
                notification.promiseId,
                notification.senderUserId,
                notification.createdAt,
            )
        }
    }
}
