package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.consts.INTERACTION_FIXED_COUNT
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.InteractionFixedEvent
import com.depromeet.whatnow.events.domainEvent.InteractionHistoryRegisterEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class InteractionHistoryRegisterHandler(
    val interactionDomainService: InteractionDomainService,
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = [InteractionHistoryRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleInteractionHistoryRegisterEvent(event: InteractionHistoryRegisterEvent) {
        logger.info { "handleInteractionHistoryRegisterEvent 이벤트 약속아이디 {${event.promiseId} , 유저아이디: ${event.userId} , 상대방 유저아이디: ${event.targetUserId} , 인터렉션타입: ${event.interactionType}" }
        val interactionCount = interactionDomainService.increment(event.promiseId, event.userId, event.interactionType)

        if (interactionCount == INTERACTION_FIXED_COUNT) {
            Events.raise(InteractionFixedEvent(event.promiseId, event.userId, event.interactionType))
        }

        val targetUser = userAdapter.queryUser(event.targetUserId)
        if (!targetUser.fcmNotification.appAlarm) {
            return
        }

        val user = userAdapter.queryUser(event.userId)

        val data: MutableMap<String, String> = mutableMapOf()
        data["notificationType"] = NotificationType.INTERACTION.name

        fcmService.sendMessageAsync(
            targetUser.fcmNotification.fcmToken,
            "이모지 투척!",
            "from. $user.nickname",
            data,
        )

        notificationDomainService.saveForInteraction(event.promiseId, user.id!!, event.interactionType, targetUser.id!!)
    }
}
