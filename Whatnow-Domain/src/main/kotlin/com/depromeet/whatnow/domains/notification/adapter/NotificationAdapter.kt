package com.depromeet.whatnow.domains.notification.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.notification.domain.Notification
import com.depromeet.whatnow.domains.notification.repository.NotificationRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

@Adapter
class NotificationAdapter(
    val notificationRepository: NotificationRepository,
) {
    fun save(notification: Notification): Notification {
        return notificationRepository.save(notification)
    }

    fun getMyNotifications(userId: Long, pageable: Pageable): Slice<Notification> {
        return notificationRepository.findAllByTargetUserIdOrderByCreatedAtDesc(userId, pageable)
    }
}
