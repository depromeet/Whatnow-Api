package com.depromeet.whatnow.api.notification.dto

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.notification.domain.InteractionAttainmentNotification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import java.time.LocalDateTime

class InteractionAttainmentNotificationResponse(
    val promiseId: Long,
    val senderUserId: Long,
    val interactionType: InteractionType,
    override val createdAt: LocalDateTime,
) : NotificationAbstract(NotificationType.INTERACTION_ATTAINMENT, createdAt) {
    companion object {
        fun from(notification: InteractionAttainmentNotification): InteractionAttainmentNotificationResponse {
            return InteractionAttainmentNotificationResponse(
                notification.promiseId,
                notification.senderUserId,
                notification.interactionType,
                notification.createdAt,
            )
        }
    }
}
