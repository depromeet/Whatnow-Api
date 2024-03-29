package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.interaction.service.InteractionDomainService
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseUserRegisterHandler(
    val progressHistoryDomainService: ProgressHistoryDomainService,
    val interactionDomainService: InteractionDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(classes = [PromiseUserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseUserRegisterEventHistory(event: PromiseUserRegisterEvent) {
        logger.info { "PromiseUserRegisterHandler 이벤트 수신 ${event.promiseId} , 유저아이디 ${event.userId}" }

        progressHistoryDomainService.initHistory(event.promiseId, event.userId)
    }

    @Async
    @TransactionalEventListener(classes = [PromiseUserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseUserRegisterEventInteraction(event: PromiseUserRegisterEvent) {
        logger.info { "PromiseUserRegisterInteractionInitHandler 이벤트 수신 약속아이디: ${event.promiseId} , 유저아이디: ${event.userId}" }
        interactionDomainService.initInteraction(event.promiseId, event.userId)
    }
}
