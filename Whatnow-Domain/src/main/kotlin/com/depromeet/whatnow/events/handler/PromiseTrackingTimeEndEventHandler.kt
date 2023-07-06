package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.events.domainEvent.PromiseTrackingTimeEndEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTrackingTimeEndEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    @Async
    @Transactional
    @TransactionalEventListener(classes = [PromiseTrackingTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(promiseTrackingTimeEndEvent: PromiseTrackingTimeEndEvent) {
        val promiseId = promiseTrackingTimeEndEvent.promiseId
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.endPromise()
    }
}
