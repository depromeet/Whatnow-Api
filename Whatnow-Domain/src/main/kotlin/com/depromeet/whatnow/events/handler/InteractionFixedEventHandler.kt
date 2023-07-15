package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.domains.interactionhistory.service.InteractionHistoryDomainService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.InteractionFixedEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class InteractionFixedEventHandler(
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
    val interactionHistoryDomainService: InteractionHistoryDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(classes = [InteractionFixedEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleInteractionFixedEvent(event: InteractionFixedEvent) {
        val promiseId = event.promiseId
        val userId = event.userId
        val interactionType = event.interactionType
        logger.info { "InteractionFixedEvent 이벤트 수신 promiseId=$promiseId, userId=$userId, interactionType=${interactionType.name}" }

        val user = userAdapter.queryUser(userId)

        val data: MutableMap<String, String> = mutableMapOf()
        data["notificationType"] = NotificationType.INTERACTION_ATTAINMENT.name
        data["promiseId"] = event.promiseId.toString()

        if (user.fcmNotification.appAlarm) {
            fcmService.sendMessageAsync(
                user.fcmNotification.fcmToken,
                "이모지 100개 달성!",
                "",
                data,
            )
        }

        interactionHistoryDomainService.queryAllByInteractionType(userId, promiseId, interactionType)
            .map { interactionHistory -> interactionHistory.targetUserId }
            .distinct()
            .forEach { targetUserId ->
                notificationDomainService.saveForInteractionAttainment(promiseId, userId, interactionType, targetUserId)
            }
    }
}
