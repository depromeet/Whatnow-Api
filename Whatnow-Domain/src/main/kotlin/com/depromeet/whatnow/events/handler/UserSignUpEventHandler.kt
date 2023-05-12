package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.events.domainEvent.UserSignUpEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class UserSignUpEventHandler {

    @Async
    @TransactionalEventListener(classes = [UserSignUpEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(userSignUpEvent: UserSignUpEvent) {
        val userId: Long = userSignUpEvent.userId
        println(userId)
    }
}