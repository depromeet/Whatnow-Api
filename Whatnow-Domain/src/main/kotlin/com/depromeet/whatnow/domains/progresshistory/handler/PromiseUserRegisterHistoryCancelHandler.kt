package com.depromeet.whatnow.domains.progresshistory.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.events.domainEvent.PromiseUserCancelEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener


@Handler
class PromiseUserRegisterHistoryCancelHandler {

    @TransactionalEventListener(classes = [PromiseUserCancelEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleDoneOrderEvent(promiseUserCancelEvent: PromiseUserCancelEvent) {



    }
}