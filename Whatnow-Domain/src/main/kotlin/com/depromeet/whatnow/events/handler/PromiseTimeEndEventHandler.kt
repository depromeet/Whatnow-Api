package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.LATE
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.WAIT
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
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
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
) {
    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(classes = [PromiseTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseTimeEndEvent(endTimePromiseEvent: PromiseTimeEndEvent) {
        val promiseId: Long = endTimePromiseEvent.promiseId
        val promise = promiseAdaptor.queryPromise(promiseId)
        val coordinate = promise.meetPlace?.coordinate
        val promiseUsers = promiseUserDomainService.findByPromiseId(promiseId)

        promiseUserDomainService.determinePromiseUserTypeByLocation(promiseUsers, coordinate)

        // 약속에 참여한 유저들 조회
        val users = promiseUsers
            .map { promiseUser -> userAdapter.queryUser(promiseUser.userId) }

        // 앱 알람 허용한 유저
        val appAlarmPermitUsers = users
            .filter { user -> user.fcmNotification.fcmToken != "" && user.fcmNotification.appAlarm }

        val lateData: MutableMap<String, String> = mutableMapOf()
        lateData["notificationType"] = NotificationType.TIMEOVER.name
        lateData["promiseId"] = promiseId.toString()

        // LATE 유저들에게 알림 발송
        val appAlarmPermitLateUserTokens = appAlarmPermitUsers
            .filter { user ->
                promiseUserDomainService.findByPromiseIdAndUserId(
                    promiseId,
                    user.id!!,
                ).promiseUserType == LATE
            }
            .map { user -> user.fcmNotification.fcmToken }
        if (appAlarmPermitLateUserTokens.isNotEmpty()) {
            fcmService.sendGroupMessageAsync(
                tokenList = appAlarmPermitLateUserTokens,
                title = "TIMEOVER!",
                content = "친구들에게 용서를 비는 사진을 보내봐!",
                data = lateData,
            )
        }

        val waitData: MutableMap<String, String> = mutableMapOf()
        waitData["notificationType"] = NotificationType.TIMEOVER.name
        waitData["promiseId"] = promiseId.toString()

        val appAlarmPermitWaitUserTokens = appAlarmPermitUsers
            .filter { user ->
                promiseUserDomainService.findByPromiseIdAndUserId(
                    promiseId,
                    user.id!!,
                ).promiseUserType == WAIT
            }
            .map { user -> user.fcmNotification.fcmToken }
        if (appAlarmPermitWaitUserTokens.isNotEmpty()) {
            fcmService.sendGroupMessageAsync(
                tokenList = appAlarmPermitWaitUserTokens,
                title = "TIMEOVER!",
                content = "친구들에게 재촉하는 사진을 보내봐!",
                data = waitData,
            )
        }

        // notification 저장
        promiseUsers.forEach { promiseUser ->
            notificationDomainService.saveForTimeOver(promiseId, promiseUser.promiseUserType, promiseUser.userId)
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
