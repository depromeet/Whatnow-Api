package com.depromeet.whatnow.domains.notification.service

import com.depromeet.whatnow.domains.notification.adapter.NotificationAdapter
import com.depromeet.whatnow.domains.notification.domain.Notification
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationDomainService(
    val notificationAdapter: NotificationAdapter,
) {
    @Transactional
    fun saveForImage(userId: Long, targetUserId: Set<Long>, targetId: Long) {
        notificationAdapter.save(
            Notification(
            notificationType = NotificationType.IMAGE,
            userId = userId,
            targetUserIds = targetUserId,
            targetId = targetId,
            interactionType = null,
            )
        )
    }

    @Transactional
    fun saveForStartSharing(targetUserId: Set<Long>, targetId: Long) {
        notificationAdapter.save(
            Notification(
                notificationType = NotificationType.START_SHARING,
                userId = null,
                targetUserIds = targetUserId,
                targetId = targetId,
                interactionType = null,
            )
        )
    }
}