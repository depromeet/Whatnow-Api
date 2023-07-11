package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.events.domainEvent.UserRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserRegisterEventHandler {

    @Async
    @TransactionalEventListener(classes = [UserRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserRegisterEvent(userRegisterEvent: UserRegisterEvent) {
        val userId: Long = userRegisterEvent.userId
    }
}
