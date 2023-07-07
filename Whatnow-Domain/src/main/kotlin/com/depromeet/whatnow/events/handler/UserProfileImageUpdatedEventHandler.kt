package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.image.adapter.UserImageAdapter
import com.depromeet.whatnow.events.domainEvent.UserProfileImageUpdatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class UserProfileImageUpdatedEventHandler(
//    val s3Service: S3Service,
    val userImageAdapter: UserImageAdapter,
) {
    @Async
    @TransactionalEventListener(classes = [UserProfileImageUpdatedEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(userProfileImageUpdatedEvent: UserProfileImageUpdatedEvent) {
        val userId = userProfileImageUpdatedEvent.userId
        val imageKey = userProfileImageUpdatedEvent.imageKey
        val fileExtension = userProfileImageUpdatedEvent.fileExtension

        // TODO [DPMBE-76] 사진 삭제기능 업데이트 시 주석 해제
        val userImages = userImageAdapter.findAllByUserId(userId)
            .filter { it.imageKey != imageKey }
            .map {
//                s3Service.deleteForUser(userId, it.imageKey, it.fileExtension)
            }
    }
}
