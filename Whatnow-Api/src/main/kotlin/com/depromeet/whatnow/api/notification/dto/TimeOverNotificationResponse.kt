package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.domain.TimeOverNotification
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import java.time.LocalDateTime

class TimeOverNotificationResponse(
    val promiseId: Long,
    val promiseUserType: PromiseUserType,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.TIMEOVER, createdAt) {
    companion object {
        fun from(notification: TimeOverNotification): TimeOverNotificationResponse {
            return TimeOverNotificationResponse(
                notification.promiseId,
                notification.promiseUserType,
                notification.createdAt,
            )
        }
    }
}
