package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.CANCEL
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.events.domainEvent.MeetPromiseUserEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTimeEndEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTimeEndEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserDomainService: PromiseUserDomainService,
) {
    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(classes = [PromiseTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(endTimePromiseEvent: PromiseTimeEndEvent) {
        val promiseId: Long = endTimePromiseEvent.promiseId
        val promise = promiseAdaptor.queryPromise(promiseId)
        val coordinate = promise.meetPlace?.coordinate
        val promiseUsers = promiseUserDomainService.findByPromiseId(promiseId)

        promiseUsers.forEach { promiseUser ->
            when (promiseUser.promiseUserType) {
                CANCEL -> return@forEach // CANCEL 일 경우 넘어간다.
                else -> {
                    val isArrived = promiseUserDomainService.isArrived(promiseUser, coordinate!!)
                    if (isArrived) {
                        promiseUser.updatePromiseUserTypeToWait()
                    } else {
                        promiseUser.updatePromiseUserTypeToLate()
                    }
                }
            }
        }
    }

    @Async
    @TransactionalEventListener(classes = [MeetPromiseUserEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleMeetPromiseUserEvent(meetPromiseUserEvent: MeetPromiseUserEvent) {
//        만났을 경우
//        for(promiseUser in promiseUsers){
//        }
    }
}
