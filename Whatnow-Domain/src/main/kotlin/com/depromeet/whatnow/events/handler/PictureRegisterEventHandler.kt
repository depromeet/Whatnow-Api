package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.events.domainEvent.PictureRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PictureRegisterEventHandler {
    @Async
    @TransactionalEventListener(classes = [PictureRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterPictureEvent(pictureRegisterEvent: PictureRegisterEvent) {
        val userId: Long = pictureRegisterEvent.userId
        val promiseId: Long = pictureRegisterEvent.promiseId
        // TODO: FCM 토큰 발급 후 약속ID 기준 참여자들에게 푸시 알림 보내기
    }
}
