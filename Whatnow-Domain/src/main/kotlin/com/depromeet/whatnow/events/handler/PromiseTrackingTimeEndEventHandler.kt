package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.events.domainEvent.PromiseTrackingTimeEndEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTrackingTimeEndEventHandler {

    @Async
    @TransactionalEventListener(classes = [PromiseTrackingTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(promiseTrackingTimeEndEvent: PromiseTrackingTimeEndEvent) {
        val promiseId = promiseTrackingTimeEndEvent.promiseId
    }
}
