package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.PromiseTrackingTimeEndEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTrackingTimeEndEventHandler(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
) {
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(classes = [PromiseTrackingTimeEndEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseTrackingTimeEndEvent(promiseTrackingTimeEndEvent: PromiseTrackingTimeEndEvent) {
        val promiseId = promiseTrackingTimeEndEvent.promiseId

        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.endPromise()

        val promiseUsers = promiseUserAdaptor.findByPromiseId(promiseId)

        // 약속에 참여한 유저들 조회
        val users = promiseUsers
            .map { promiseUser -> userAdapter.queryUser(promiseUser.userId) }

        // 앱 알람 허용한 유저
        val appAlarmPermitUsers = users
            .filter { user -> user.fcmNotification.fcmToken != null && user.fcmNotification.appAlarm }

        val data: MutableMap<String, String> = mutableMapOf()
        data["notificationType"] = NotificationType.END_SHARING.name
        data["promiseId"] = promiseId.toString()

        // 앱 알람 허용한 유저에게 알람 보내기
        if (appAlarmPermitUsers.isNotEmpty()) {
            fcmService.sendGroupMessageAsync(
                appAlarmPermitUsers.map { user -> user.fcmNotification.fcmToken!! },
                "위치 공유 종료!",
                "어떤 일이 있었는지 돌아보자!",
                data,
            )
        }

        // notification 저장
        users.forEach { user ->
            notificationDomainService.saveForEndSharing(promiseId, user.id!!)
        }
    }
}
