package com.depromeet.whatnow.domains.interaction.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseUserRegisterInteractionInitHandler(
    val interactionDomainService: InteractionDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @TransactionalEventListener(classes = [PromiseUserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseUserRegisterEvent(event: PromiseUserRegisterEvent) {
        logger.info { "PromiseUserRegisterInteractionInitHandler 이벤트 수신 약속아이디: ${event.promiseId} , 유저아이디: ${event.userId}" }
        interactionDomainService.initInteraction(event.promiseId, event.userId)
    }
}
