package com.depromeet.whatnow.api.notification.controller

import com.depromeet.whatnow.api.notification.dto.HighlightsResponse
import com.depromeet.whatnow.api.notification.dto.NotificationResponse
import com.depromeet.whatnow.api.notification.usecase.NotificationHighlightsReadUseCase
import com.depromeet.whatnow.api.notification.usecase.NotificationReadUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "8. [알림]")
@RequestMapping("/v1/notifications")
@SecurityRequirement(name = "access-token")
class NotificationController(
    val notificationReadUseCase: NotificationReadUseCase,
    val notificationHighlightsReadUseCase: NotificationHighlightsReadUseCase,
) {
    @Operation(summary = "자신의 알림 조회")
    @GetMapping
    fun getMyNotifications(
        @ParameterObject @PageableDefault
        pageable: Pageable,
    ): NotificationResponse {
        return notificationReadUseCase.execute(pageable)
    }

    @Operation(summary = "하이라이트 조회", description = "약속 ID로 하이라이트를 조회합니다")
    @GetMapping("/highlights/promises/{promise-id}")
    fun getHighlights(
        @PathVariable("promise-id") promiseId: Long,
        @ParameterObject @PageableDefault
        pageable: Pageable,
    ): HighlightsResponse {
        return notificationHighlightsReadUseCase.execute(promiseId, pageable)
    }
}
