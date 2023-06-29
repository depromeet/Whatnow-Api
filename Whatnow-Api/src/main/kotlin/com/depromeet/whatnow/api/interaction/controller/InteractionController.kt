package com.depromeet.whatnow.api.interaction.controller

import com.depromeet.whatnow.api.interaction.usecase.InteractionSendUseCase
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "7. [인터렉션]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class InteractionController(
    val interactionSendUseCase: InteractionSendUseCase,
) {
    @PostMapping("/promises/{promiseId}/interactions/{interactionType}/target/{targetUserId}")
    @Operation(summary = "인터렉션을 발송합니다.")
    fun sendInteraction(
        @PathVariable promiseId: Long,
        @PathVariable interactionType: InteractionType,
        @PathVariable targetUserId: Long,
    ): ResponseEntity<Unit> {
        interactionSendUseCase.execute(promiseId, interactionType, targetUserId)
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Unit)
    }
}
