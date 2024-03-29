package com.depromeet.whatnow.events.handler

import com.depromeet.whatnow.annotation.Handler
import com.depromeet.whatnow.config.fcm.FcmService
import com.depromeet.whatnow.domains.notification.domain.NotificationType
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.LATE
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.WAIT
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.events.domainEvent.PromiseImageRegisterEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Handler
class ImageRegisterEventHandler(
    val promiseUserAdapter: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val fcmService: FcmService,
    val notificationDomainService: NotificationDomainService,
) {
    @Async
    @TransactionalEventListener(classes = [PromiseImageRegisterEvent::class], phase = TransactionPhase.AFTER_COMMIT)
    fun handlePromiseImageRegisterEvent(promiseImageRegisterEvent: PromiseImageRegisterEvent) {
        val userId: Long = promiseImageRegisterEvent.userId
        val promiseId: Long = promiseImageRegisterEvent.promiseId
        val imageKey: String = promiseImageRegisterEvent.imageKey

        // 사진을 보낸 유저가 Late인지 Wait인지 확인하기 위한 PromiseUser 조회
        val promiseUser = promiseUserAdapter.findByPromiseIdAndUserId(promiseId, userId)

        // 약속에 참여한 유저들 조회 (사진 보낸 유저 제외)
        val usersExcludingSelf = promiseUserAdapter.findByPromiseId(promiseId)
            .filter { promiseUser -> promiseUser.userId != userId }
            .map { promiseUser -> userAdapter.queryUser(promiseUser.userId) }

        // 앱 알람 허용한 유저
        val appAlarmPermitUsers = usersExcludingSelf.filter { user ->
            user.fcmNotification.fcmToken != "" && user.fcmNotification.appAlarm
        }

        val data: MutableMap<String, String> = mutableMapOf()
        data["notificationType"] = NotificationType.IMAGE.name
        data["promiseId"] = promiseId.toString()
        data["imageKey"] = imageKey

        // 앱 알람 허용한 유저에게 알람 보내기
        if (appAlarmPermitUsers.isNotEmpty()) {
            when (promiseUser.promiseUserType) {
                LATE -> {
                    fcmService.sendGroupMessageAsync(
                        tokenList = appAlarmPermitUsers.map { user -> user.fcmNotification.fcmToken!! },
                        title = "지각한 친구의 사진 도착",
                        content = "지각한 친구가 보낸 사진을 확인해봐!",
                        data = data,
                    )
                }
                WAIT -> {
                    fcmService.sendGroupMessageAsync(
                        tokenList = appAlarmPermitUsers.map { user -> user.fcmNotification.fcmToken!! },
                        title = "도착한 친구들의 사진 도착",
                        content = "도착한 친구들이 보낸 사진을 확인해봐!",
                        data = data,
                    )
                }
            }
        }

        // notification 저장
        usersExcludingSelf
            .forEach { targetUser ->
                notificationDomainService.saveForImage(
                    promiseUser.promiseUserType,
                    userId,
                    targetUser.id!!,
                    promiseId,
                    imageKey,
                )
            }
    }
}
