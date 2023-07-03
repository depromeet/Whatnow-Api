package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.events.domainEvent.PromiseImageRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class ImageRegisterEventHandler {
    @Async
    @TransactionalEventListener(classes = [PromiseImageRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterPictureEvent(promiseImageRegisterEvent: PromiseImageRegisterEvent) {
        val userId: Long = promiseImageRegisterEvent.userId
        val promiseId: Long = promiseImageRegisterEvent.promiseId
        // TODO: FCM 토큰 발급 후 약속ID 기준 참여자들에게 푸시 알림 보내기
    }
}
