package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService
import com.depromeet.whatnow.events.domainEvent.InteractionHistoryRegisterEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class InteractionHistoryRegisterHandler(
    val interactionDomainService: InteractionDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(classes = [InteractionHistoryRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleInteractionHistoryRegisterEvent(event: InteractionHistoryRegisterEvent) {
        logger.info { "handleInteractionHistoryRegisterEvent 이벤트 약속아이디 {${event.promiseId} , 유저아이디: ${event.userId} , 상대방 유저아이디: ${event.targetUserId} , 인터렉션타입: ${event.interactionType}" }
        interactionDomainService.increment(event.promiseId, event.userId, event.interactionType)
    }
}
