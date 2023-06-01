package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.domains.promise.service.PromiseDomainService
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.events.domainEvent.EndTimePromiseEvent
import com.depromeet.whatnow.events.domainEvent.MeetPromiseUserEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class EndTimePromiseUserEventHandler(
    val promiseDomainService: PromiseDomainService,
    val promiseUserDomainService: PromiseUserDomainService,
) {
    @Async
    @TransactionalEventListener(classes = [EndTimePromiseEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterUserEvent(endTimePromiseEvent: EndTimePromiseEvent) {
        val promiseId: Long = endTimePromiseEvent.promiseId
        val promiseUsers = promiseUserDomainService.findByPromiseId(promiseId)
        for (promiseUser in promiseUsers) {
            // 위치기반 서비스가 완성되면 그때 주석 해제 예정

           /* if(promiseDomainService.isArrived(promiseId,promiseUser.userId)){
            // 유저가 위치가 약속 장소 도착이면 wait 로 상태 변경
                promiseUser.updatePromiseUserTypeToWait()
            }
            else{
                if(promiseUser.promiseUserType ==CANCEL) {
                    // 약속을 취소한 사람은 update 하지 않는다.
                    continue
                }
                // 유저가 위치가 약속 장소 도착이 아니면 late 로 상태 변경
                promiseUser.updatePromiseUserTypeToLate()

            }*/
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
