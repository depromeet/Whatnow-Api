package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.notification.domain.InteractionNotification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import java.time.LocalDateTime

class InteractionNotificationResponse(
    val promiseId: Long,
    val senderUserId: Long,
    val interactionType: InteractionType,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.INTERACTION, createdAt) {
    companion object {
        fun from(notification: InteractionNotification): InteractionNotificationResponse {
            return InteractionNotificationResponse(
                notification.promiseId,
                notification.senderUserId,
                notification.interactionType,
                notification.createdAt,
            )
        }
    }
}
