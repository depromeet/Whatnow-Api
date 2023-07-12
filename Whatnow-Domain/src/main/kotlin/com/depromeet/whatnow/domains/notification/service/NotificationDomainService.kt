package com.depromeet.whatnow.domains.notification.service

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
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

    @Transactional
    fun saveForEndSharing(targetUserIds: Set<Long>, promiseId: Long) {
        notificationAdapter.save(Notification.createForEndSharing(targetUserIds, promiseId))
    }

    @Transactional
    fun saveForTimeOver(targetUserIds: Set<Long>, promiseId: Long) {
        notificationAdapter.save(Notification.createForTimeOver(targetUserIds, promiseId))
    }

    @Transactional
    fun saveForInteraction(userId: Long, targetUserId: Long, interactionType: InteractionType) {
        notificationAdapter.save(Notification.createForInteraction(userId, targetUserId, interactionType))
    }

    @Transactional
    fun saveForInteractionAttainment(userId: Long, senderUserIds: Set<Long>, interactionType: InteractionType) {
        notificationAdapter.save(Notification.createForInteractionAttainment(userId, senderUserIds, interactionType))
    }
}
