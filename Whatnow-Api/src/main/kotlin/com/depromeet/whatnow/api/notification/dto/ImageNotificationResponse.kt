package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.notification.domain.ImageNotification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import java.time.LocalDateTime

class ImageNotificationResponse(
    val senderUserPromiseUserType: PromiseUserType,
    val senderUserId: Long,
    val promiseId: Long,
    val imageKey: String,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.IMAGE, createdAt) {
    companion object {
        fun from(notification: ImageNotification): ImageNotificationResponse {
            return ImageNotificationResponse(
                notification.senderUserPromiseUserType,
                notification.senderUserId,
                notification.promiseId,
                notification.imageKey,
                notification.createdAt,
            )
        }
    }
}
