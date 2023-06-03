package com.depromeet.whatnow.api.promiseprogress.controller

import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.api.promiseprogress.usecase.ProgressHistoryChangeUseCase
import com.depromeet.whatnow.api.promiseprogress.usecase.ProgressHistoryReadUseCase
import com.depromeet.whatnow.api.promiseprogress.usecase.PromiseProgressReadUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/promises")
@Tag(name = "5. [약속진행상태]")
@SecurityRequirement(name = "access-token")
class PromiseProgressController(
    val promiseProgressReadUseCase: PromiseProgressReadUseCase,
    val promiseHistoryChangeUseCase: ProgressHistoryChangeUseCase,
    val promiseHistoryReadUseCase: ProgressHistoryReadUseCase,
) {

    @GetMapping("/progresses")
    fun getPromiseProgresses(): List<PromiseProgressGroupElement> {
        return promiseProgressReadUseCase.execute()
    }

    @GetMapping("/{promiseId}/users/{userId}")
    fun getUserProgress(@PathVariable promiseId: Long, @PathVariable userId: Long): UserProgressResponse {
        return promiseHistoryReadUseCase.execute(promiseId, userId)
    }

    @PatchMapping("/{promiseId}/progresses/{progressCode}")
    fun changePromiseProgress(@PathVariable progressCode: String, @PathVariable promiseId: Long): UserProgressResponse {
        return promiseHistoryChangeUseCase.execute(promiseId, progressCode)
    }
}
