package com.depromeet.whatnow.api.promiseprogress.controller

import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse
import com.depromeet.whatnow.api.promiseprogress.usecase.ProgressHistoryChangeUseCase
import com.depromeet.whatnow.api.promiseprogress.usecase.ProgressHistoryReadUseCase
import com.depromeet.whatnow.api.promiseprogress.usecase.PromiseProgressReadUseCase
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
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
    @Operation(summary = "약속 진행 단계들을 이넘으로 확인합니다. 주의!! 스웨거 이넘 예시 값과 실제 요청했을 때 값이 달라요 실제 요청값 기준으로 해주세요")
    fun getPromiseProgresses(): List<PromiseProgressGroupElement> {
        return promiseProgressReadUseCase.execute()
    }

    @GetMapping("/{promiseId}/users/{userId}")
    @Operation(summary = "해당 약속 그 유저의 진행상태를 확인합니다. 주의!! 스웨거 이넘 예시 값과 실제 요청했을 때 값이 달라요 실제 요청값 기준으로 해주세요")
    fun getUserProgress(@PathVariable promiseId: Long, @PathVariable userId: Long): UserProgressResponse {
        return promiseHistoryReadUseCase.execute(promiseId, userId)
    }

    @PatchMapping("/{promiseId}/progresses/{progressCode}")
    @Operation(summary = "해당 약속 그 유저의 진행상태를 변경합니다. 주의!! 스웨거 이넘 예시 값과 실제 요청했을 때 값이 달라요 실제 요청값 기준으로 해주세요")
    fun changePromiseProgress(@PathVariable progressCode: PromiseProgress, @PathVariable promiseId: Long): UserProgressResponse {
        return promiseHistoryChangeUseCase.execute(promiseId, progressCode)
    }
}
