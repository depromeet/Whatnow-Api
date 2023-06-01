package com.depromeet.whatnow.api.promiseuser.controller

import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.api.promiseuser.usecase.PromiseUserReadUseCase
import com.depromeet.whatnow.api.promiseuser.usecase.PromiseUserRecordUseCase
import com.depromeet.whatnow.common.vo.CoordinateVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/")
@Tag(name = "4. [약속 유저]")
@SecurityRequirement(name = "access-token")
class PromiseUserController(
    val promiseUserRecordUseCase: PromiseUserRecordUseCase,
    val promiseUserReadUseCase: PromiseUserReadUseCase,
) {
    @Operation(summary = "약속 유저(promise) 생성", description = "약속에 유저가 참여합니다.")
    @PostMapping("promise/{promise-id}/users")
    fun joinPromise(@PathVariable("promise-id") promiseId: Long, userId: Long, userLocation: CoordinateVo): PromiseUserDto {
        return promiseUserRecordUseCase.createPromiseUser(promiseId, userId, userLocation)
    }

    @Operation(summary = "약속 id로 약속 유저(promiseUser) 조회", description = "약속ID 로 약속 유저를 조회합니다.")
    @GetMapping("promises/{promiseId}/promise-users")
    fun getPromiseUser(@PathVariable("promise-id") promiseId: Long): List<PromiseUserDto> {
        return promiseUserReadUseCase.findByPromiseId(promiseId)
    }

    @Operation(summary = "유저가 약속을 취소(상태로 변경)", description = "userId로 참여한 약속 유저를 취소(cancel) 합니다.")
    @GetMapping("promises/{promise-id}/users/{user-id}/status/{status}")
    fun getPromiseUserByPromiseUserType(@PathVariable("promise-id") promiseId: Long, @PathVariable("user-id") userId: Long, @PathVariable("status") status: String): List<PromiseUserDto> {
        return promiseUserReadUseCase.findPromiseUserByPromiseUserType(promiseId, userId, status)
    }

    @Operation(summary = "유저가 약속을 취소(상태로 변경)", description = "userId로 참여한 약속 유저를 취소(cancel) 합니다.")
    @PutMapping("promises/{promise-id}/users/{user-id}/status/{status}")
    fun cancelPromise(@PathVariable("promise-id") promiseId: Long, @PathVariable("user-id") userId: Long, @PathVariable("status") status: String): PromiseUserDto {
        return promiseUserRecordUseCase.updatePromiseUserType(promiseId, userId, status)
    }
}
