package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.config.s3.S3Service
import com.depromeet.whatnow.events.domainEvent.PromiseImageDeletedEvent
import com.depromeet.whatnow.events.domainEvent.UserImageDeletedEvent
import mu.KLogger
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class ImageDeletedEventHandler(
    val s3Service: S3Service,
) {
    val logger: KLogger = KotlinLogging.logger {}

    @Async
    @TransactionalEventListener(classes = [UserImageDeletedEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserImageDeletedEvent(userImageDeletedEvent: UserImageDeletedEvent) {
        val imageKey = userImageDeletedEvent.imageKey
        val userId = userImageDeletedEvent.userId
        val imageFileExtension = userImageDeletedEvent.imageFileExtension
        logger.info { "UserImageDeletedEvent 이벤트 수신 imageKey=$imageKey, userId=$userId, fileExtension=$imageFileExtension" }

        s3Service.deleteForUser(userId, imageKey, imageFileExtension)
    }

    @Async
    @TransactionalEventListener(classes = [PromiseImageDeletedEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseImageDeletedEvent(promiseImageDeletedEvent: PromiseImageDeletedEvent) {
        val imageKey = promiseImageDeletedEvent.imageKey
        val promiseId = promiseImageDeletedEvent.promiseId
        val imageFileExtension = promiseImageDeletedEvent.imageFileExtension
        logger.info { "promiseImageDeletedEvent 이벤트 수신 imageKey=$imageKey, promiseId=$promiseId, fileExtension=$imageFileExtension" }

        s3Service.deleteForPromise(promiseId, imageKey, imageFileExtension)
    }
}
