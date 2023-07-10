package com.depromeet.whatnow.domains.notification.service

import com.depromeet.whatnow.domains.notification.adapter.NotificationAdapter
import com.depromeet.whatnow.domains.notification.domain.Notification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationDomainService(
    val notificationAdapter: NotificationAdapter,
) {
    @Transactional
    fun saveForImage(userId: Long, targetUserId: Set<Long>, promiseImageId: Long) {
        notificationAdapter.save(Notification.createForImage(userId, targetUserId, promiseImageId))
    }

    @Transactional
    fun saveForStartSharing(targetUserIds: Set<Long>, promiseId: Long) {
        notificationAdapter.save(Notification.createForStartSharing(targetUserIds, promiseId))
    }

    fun saveForTimeOver(targetUserIds: Set<Long>, promiseId: Long) {
        notificationAdapter.save(Notification.createForTimeOver(targetUserIds, promiseId))
    }
}
