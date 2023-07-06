package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.events.domainEvent.PromiseTimeStartEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTimeStartEventHandler(
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    @Async
    @Transactional
    @TransactionalEventListener(classes = [PromiseTimeStartEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(promiseTimeStartEvent: PromiseTimeStartEvent) {
        val promiseId = promiseTimeStartEvent.promiseId
        val promiseUsers = promiseUserAdaptor.findByPromiseId(promiseId)

        promiseUsers.forEach { promiseUser ->
            promiseUser.updatePromiseUserTypeToWait()
            promiseUser.userLocationInit()
        }
    }
}
