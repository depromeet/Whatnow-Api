package com.depromeet.whatnow.domains.notification.service

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.notification.adapter.NotificationAdapter
import com.depromeet.whatnow.domains.notification.domain.EndSharingNotification
import com.depromeet.whatnow.domains.notification.domain.ImageNotification
import com.depromeet.whatnow.domains.notification.domain.InteractionAttainmentNotification
import com.depromeet.whatnow.domains.notification.domain.InteractionNotification
import com.depromeet.whatnow.domains.notification.domain.StartSharingNotification
import com.depromeet.whatnow.domains.notification.domain.TimeOverNotification
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationDomainService(
    val notificationAdapter: NotificationAdapter,
) {
    @Transactional
    fun saveForImage(senderPromiseUserType: PromiseUserType, userId: Long, targetUserId: Long, promiseId: Long, imageKey: String) {
        notificationAdapter.save(ImageNotification(senderPromiseUserType, userId, promiseId, imageKey, targetUserId))
    }

    @Transactional
    fun saveForStartSharing(promiseId: Long, targetUserId: Long) {
        notificationAdapter.save(StartSharingNotification(promiseId, targetUserId))
    }

    @Transactional
    fun saveForEndSharing(promiseId: Long, targetUserId: Long) {
        notificationAdapter.save(EndSharingNotification(promiseId, targetUserId))
    }

    @Transactional
    fun saveForTimeOver(promiseId: Long, promiseUserType: PromiseUserType, targetUserId: Long) {
        notificationAdapter.save(TimeOverNotification(promiseId, promiseUserType, targetUserId))
    }

    @Transactional
    fun saveForInteraction(promiseId: Long, senderUserId: Long, interactionType: InteractionType, targetUserId: Long) {
        notificationAdapter.save(InteractionNotification(promiseId, senderUserId, interactionType, targetUserId))
    }

    @Transactional
    fun saveForInteractionAttainment(promiseId: Long, senderUserId: Long, interactionType: InteractionType, targetUserId: Long) {
        notificationAdapter.save(InteractionAttainmentNotification(promiseId, senderUserId, interactionType, targetUserId))
    }
}
