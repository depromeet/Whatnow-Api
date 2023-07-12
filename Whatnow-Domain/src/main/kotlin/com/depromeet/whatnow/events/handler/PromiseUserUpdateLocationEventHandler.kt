package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseUserUpdateLocationEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseUserUpdateLocationEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val promiseUserDomainService: PromiseUserDomainService,
) {
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = [PromiseUserUpdateLocationEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlerPromiseUserUpdateLocationEventHandler(promiseUserUpdateLocationEvent: PromiseUserUpdateLocationEvent) {
        val promiseId = promiseUserUpdateLocationEvent.promiseId
        val promise = promiseAdaptor.queryPromise(promiseId)
        val promiseUser = promiseUserAdaptor.findByPromiseIdAndUserId(promiseUserUpdateLocationEvent.promiseId, promiseUserUpdateLocationEvent.userId)
        if (promiseUserDomainService.isArrived(promiseUser, promise.meetPlace!!.coordinate)) {
            // 활성화된 약속이 종료되기 전일 때
            if (promise.isBeforePromiseEndTime() && promise.isActive()) {
                // 약속유저 상태 도착 상태(WAIT)로 변경
                promiseUser.updatePromiseUserTypeToWait()
            }
            // 활성화된 약속이 종료되고 나서 도착 시 사전에 LATE처리를 해서 별도의 처리가 필요 없음
        }
    }
}
