package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.domains.invitecode.domain.PromiseActiveRedisEntity
import com.depromeet.whatnow.domains.promise.service.PromiseDomainService
import com.depromeet.whatnow.domains.promiseactive.adapter.PromiseActiveAdapter
import com.depromeet.whatnow.events.domainEvent.PromiseRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.time.Duration
import java.time.LocalDateTime

@Handler
class PromiseActivationEventHandler(
    val promiseDomainService: PromiseDomainService,
    val promiseActiveAdapter: PromiseActiveAdapter,
) {
    @Async
    @TransactionalEventListener(classes = [PromiseRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseRegisterEvent(promiseRegisterEvent: PromiseRegisterEvent) {
        val promise = promiseDomainService.findByPromiseId(promiseRegisterEvent.promiseId)

        val now = LocalDateTime.now()

        // 약속 시작 시간까지 남은 시간(초)
        val promiseStartAndTrackingStartTime = Duration.between(now, promise.endTime.minusHours(1)).seconds

        // 약속 종료 시간까지 남은 시간(초)
        val promiseEndTime = Duration.between(now, promise.endTime).seconds

        // 트래킹 종료 시간까지 남은 시간(초)
        val trackingEndTime = Duration.between(now, promise.endTime.plusMinutes(30)).seconds

        promiseActiveAdapter.save(PromiseActiveRedisEntity("EXPIRE_EVENT_PROMISE_TIME_START_${promise.id}", promiseStartAndTrackingStartTime))
        promiseActiveAdapter.save(PromiseActiveRedisEntity("EXPIRE_EVENT_PROMISE_TIME_END_${promise.id}", promiseEndTime))
        promiseActiveAdapter.save(PromiseActiveRedisEntity("EXPIRE_EVENT_TRACKING_TIME_START_${promise.id}", trackingEndTime))
    }
}
