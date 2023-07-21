package com.depromeet.whatnow.api.notification.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.notification.dto.ArrivalNotificationResponse
import com.depromeet.whatnow.api.notification.dto.HighlightsResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.notification.domain.ArrivalNotification
import com.depromeet.whatnow.domains.notification.exception.NotHighlightsTypeException
import com.depromeet.whatnow.domains.notification.service.NotificationDomainService
import org.springframework.data.domain.Pageable

@UseCase
class NotificationHighlightsReadUseCase(
    val notificationDomainService: NotificationDomainService,
) {
    fun execute(promiseId: Long, pageable: Pageable): HighlightsResponse {
        val userId = SecurityUtils.currentUserId
        val highlights = notificationDomainService.getNotificationHighlights(promiseId, userId, pageable)
            .map { notification ->
                when (notification) {
                    is ArrivalNotification -> {
                        ArrivalNotificationResponse.from(notification)
                    }
                    // TODO: 만났다 이벤트 생성 된 이후 MEET Type Notification 추가
                    else -> throw NotHighlightsTypeException.EXCEPTION
                }
            }.toList()
        return HighlightsResponse(highlights)
    }

    fun executeTop3(promiseId: Long): HighlightsResponse {
        val listMap = notificationDomainService.getNotificationHighlightsTop3(promiseId)
            .map { notification ->
                when (notification) {
                    is ArrivalNotification ->
                        ArrivalNotificationResponse.from(notification)
                    // TODO: 만났다 이벤트 생성 된 이후 MEET Type Notification 추가
                    else -> throw NotHighlightsTypeException.EXCEPTION
                }
            }
            .groupBy { it.senderUserId }

        val result = listMap.values.map { it.first() }
        return HighlightsResponse(result.sortedByDescending { it.createdAt })
    }
}
