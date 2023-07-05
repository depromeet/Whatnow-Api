package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.CANCEL
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.events.domainEvent.MeetPromiseUserEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTimeEndEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUpdateEndTimeEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTimeEndEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserDomainService: PromiseUserDomainService,
) {
    @Async
    @TransactionalEventListener(classes = [PromiseTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(endTimePromiseEvent: PromiseUpdateEndTimeEvent) {
        val promiseId: Long = endTimePromiseEvent.promiseId
        val promise = promiseAdaptor.queryPromise(promiseId)
        val coordinate = promise.meetPlace?.coordinate
        val promiseUsers = promiseUserDomainService.findByPromiseId(promiseId)
        for (promiseUser in promiseUsers) {
            when (promiseUser.promiseUserType) {
                // 만약 유저가 약속을 취소한 상태라면 update 하지 않는다.
                CANCEL -> continue

                else -> {
                    // 만약 유저가 위치가 약속 장소 도착이면 wait 로 상태 변경
                    if (promiseUserDomainService.isArrived(promiseUser, coordinate!!)) {
                        promiseUser.updatePromiseUserTypeToWait()
                    }
                    // 만약 유저가 위치가 약속 장소 도착이 아니면 late 로 상태 변경
                    else {
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
