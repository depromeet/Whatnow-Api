package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.progresshistory.service.ProgressHistoryDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseUserCancelEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseUserCancelHandler(
    val progressHistoryDomainService: ProgressHistoryDomainService,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(classes = [PromiseUserCancelEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseUserCancelEvent(event: PromiseUserCancelEvent) {
        logger.info { "PromiseUserRegisterHistoryCancelHandler 이벤트 수신 ${event.promiseId} , 유저아이디 ${event.userId}" }

        progressHistoryDomainService.deleteHistory(event.promiseId, event.userId)
    }
}
