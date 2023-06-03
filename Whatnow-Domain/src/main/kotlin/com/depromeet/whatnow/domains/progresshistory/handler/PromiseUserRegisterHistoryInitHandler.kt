package com.depromeet.whatnow.domains.progresshistory.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener


@Handler
class PromiseUserRegisterHistoryInitHandler {

    @TransactionalEventListener(classes = [PromiseUserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleDoneOrderEvent(promiseUserRegisterEvent: PromiseUserRegisterEvent) {



    }
}