package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.events.domainEvent.ImageRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class ImageRegisterEventHandler {
    @Async
    @TransactionalEventListener(classes = [ImageRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterPictureEvent(imageRegisterEvent: ImageRegisterEvent) {
        val userId: Long = imageRegisterEvent.userId
        val promiseId: Long = imageRegisterEvent.promiseId
        // TODO: FCM 토큰 발급 후 약속ID 기준 참여자들에게 푸시 알림 보내기
    }
}
