package com.depromeet.whatnow.domains.progresshistory.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseUserRegisterHistoryInitHandler(
    val progressHistoryDomainService: ProgressHistoryDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @TransactionalEventListener(classes = [PromiseUserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleDoneOrderEvent(event: PromiseUserRegisterEvent) {
        logger.info { "PromiseUserRegisterHistoryInitHandler 이벤트 수신 ${event.promiseId} , 유저아이디 ${event.userId}" }

        progressHistoryDomainService.initHistory(event.promiseId, event.userId)
    }
}
