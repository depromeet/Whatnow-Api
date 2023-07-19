package com.depromeet.whatnow.api.notification.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.notification.dto.EndSharingNotificationResponse
import com.depromeet.whatnow.api.notification.dto.ImageNotificationResponse
import com.depromeet.whatnow.api.notification.dto.InteractionAttainmentNotificationResponse
import com.depromeet.whatnow.api.notification.dto.InteractionNotificationResponse
import com.depromeet.whatnow.api.notification.dto.NotificationResponse
import com.depromeet.whatnow.api.notification.dto.StartSharingNotificationResponse
import com.depromeet.whatnow.api.notification.dto.TimeOverNotificationResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.notification.domain.EndSharingNotification
import com.depromeet.whatnow.domains.notification.domain.ImageNotification
import com.depromeet.whatnow.domains.notification.domain.InteractionAttainmentNotification
import com.depromeet.whatnow.domains.notification.domain.InteractionNotification
import com.depromeet.whatnow.domains.notification.domain.StartSharingNotification
import com.depromeet.whatnow.domains.notification.domain.TimeOverNotification
import com.depromeet.whatnow.domains.notification.exception.UnknownNotificationTypeException
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import org.springframework.data.domain.Pageable

@UseCase
class NotificationReadUseCase(
    val notificationDomainService: NotificationDomainService,
) {
    fun execute(pageable: Pageable): NotificationResponse {
        val userId = SecurityUtils.currentUserId
        val notifications = notificationDomainService.getMyNotifications(userId, pageable)
            .map { notification ->
                when (notification) {
                    is ImageNotification -> {
                        ImageNotificationResponse.from(notification)
                    }
                    is InteractionNotification -> {
                        InteractionNotificationResponse.from(notification)
                    }
                    is InteractionAttainmentNotification -> {
                        InteractionAttainmentNotificationResponse.from(notification)
                    }
                    is StartSharingNotification -> {
                        StartSharingNotificationResponse.from(notification)
                    }
                    is EndSharingNotification -> {
                        EndSharingNotificationResponse.from(notification)
                    }
                    is TimeOverNotification -> {
                        TimeOverNotificationResponse.from(notification)
                    }
                    else -> throw UnknownNotificationTypeException.EXCEPTION
                }
            }
            .groupBy { it.createdAt.toLocalDate() }

        return NotificationResponse(notifications)
    }
}
