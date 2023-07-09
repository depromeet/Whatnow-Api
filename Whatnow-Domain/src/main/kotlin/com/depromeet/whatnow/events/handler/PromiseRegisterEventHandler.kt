package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.events.domainEvent.PromiseRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class PromiseRegisterEventHandler(
    val promiseUserDomainService: PromiseUserDomainService,
) {
    @Async
    @TransactionalEventListener(classes = [PromiseRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handleRegisterPictureEvent(promiseRegisterEvent: PromiseRegisterEvent) {
        // 약속 등록 시 방장의 PromiseUser 생성하기
        val promiseUser = PromiseUser(
            promiseId = promiseRegisterEvent.promiseId,
            userId = promiseRegisterEvent.userId,
            userLocation = CoordinateVo(
                latitude = 0.0,
                longitude = 0.0)
        )
        promiseUserDomainService.createPromiseUser(promiseUser)
    }
}
