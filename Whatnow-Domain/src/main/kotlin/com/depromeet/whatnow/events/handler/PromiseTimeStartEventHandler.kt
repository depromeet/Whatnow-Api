package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.PromiseTimeStartEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class PromiseTimeStartEventHandler(
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
) {
    @Async
    @Transactional
    @TransactionalEventListener(classes = [PromiseTimeStartEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseTimeStartEvent(promiseTimeStartEvent: PromiseTimeStartEvent) {
        val promiseId = promiseTimeStartEvent.promiseId
        val promiseUsers = promiseUserAdaptor.findByPromiseId(promiseId)

        // 약속에 참여한 유저들 조회
        val users = promiseUsers
            .map { promiseUser -> userAdapter.queryUser(promiseUser.userId) }

        // 앱 알람 허용한 유저
        val appAlarmPermitUsers = users
            .filter { user -> user.fcmNotification.fcmToken != "" && user.fcmNotification.appAlarm }

        val data: MutableMap<String, String> = mutableMapOf()
        data["notificationType"] = NotificationType.START_SHARING.name
        data["promiseId"] = promiseId.toString()

        // 앱 알람 허용한 유저에게 알람 보내기
        if (appAlarmPermitUsers.isNotEmpty()) {
            fcmService.sendGroupMessageAsync(
                tokenList = appAlarmPermitUsers.map { user -> user.fcmNotification.fcmToken!! },
                title = "위치 공유 시작!",
                content = "공유 시작! 지도를 확인해봐!",
                data = data,
            )
        }

        // notification 저장
        users.forEach { user ->
            notificationDomainService.saveForStartSharing(promiseId, user.id!!)
        }
    }
}
