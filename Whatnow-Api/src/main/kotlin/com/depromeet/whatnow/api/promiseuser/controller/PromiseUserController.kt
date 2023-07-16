package com.depromeet.whatnow.api.promiseuser.controller

import com.depromeet.whatnow.api.promiseuser.dto.PromiseLocationDto
import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.api.promiseuser.usecase.PromiseUserReadUseCase
import com.depromeet.whatnow.api.promiseuser.usecase.PromiseUserRecordUseCase
import com.depromeet.whatnow.common.aop.verify.InviteCodeLength
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
@Tag(name = "4. [약속 유저]")
@SecurityRequirement(name = "access-token")
class PromiseUserController(
    val promiseUserRecordUseCase: PromiseUserRecordUseCase,
    val promiseUserReadUseCase: PromiseUserReadUseCase,
) {
    @Operation(summary = "약속 유저(promise) 생성", description = "약속에 유저가 참여합니다.")
    @PostMapping("/promises/{promise-id}/users")
    fun joinPromise(@PathVariable("promise-id") promiseId: Long, userId: Long, userLocation: CoordinateVo): PromiseUserDto {
        return promiseUserRecordUseCase.createPromiseUser(promiseId, userId, userLocation)
    }

    @Operation(summary = "약속 코드로 약속 유저 생성", description = "약속 코드로 약속에 유저가 참여합니다.")
    @PostMapping("/promises/users/join")
    fun joinPromise(
        @InviteCodeLength
        @RequestParam("invite-codes")
        inviteCode: String,
    ): PromiseUserDto {
        return promiseUserRecordUseCase.createPromiseUserByInviteCode(inviteCode)
    }

    @Operation(summary = "약속 id로 약속 유저(promiseUser) 조회", description = "약속ID 로 약속 유저를 조회합니다.")
    @GetMapping("/promises/{promiseId}/users")
    fun getPromiseUser(@PathVariable("promise-id") promiseId: Long): List<PromiseUserDto> {
        return promiseUserReadUseCase.findByPromiseId(promiseId)
    }

    @Operation(summary = "약속id 와 유저 id 로 약속에 참여한 유저의 정보를 조회", description = "약속ID 와 유저ID 로 약속에 참여한 유저의 정보를 조회합니다.")
    @GetMapping("/promises/{promise-id}/users/{user-id}")
    fun getPromiseUserByPromiseUserType(@PathVariable("promise-id") promiseId: Long, @PathVariable("user-id") userId: Long): PromiseUserDto {
        return promiseUserReadUseCase.findPromiseUserByPromiseIdAndUserId(promiseId, userId)
    }

    @Operation(summary = "유저가 약속을 취소(상태로 변경)", description = "userId로 참여한 약속 유저를 취소(cancel) 합니다.")
    @PutMapping("/promises/{promise-id}/users/{user-id}/status/{status}")
    fun cancelPromise(@PathVariable("promise-id") promiseId: Long, @PathVariable("user-id") userId: Long, @PathVariable("status") promiseUserType: PromiseUserType): PromiseUserDto {
        return promiseUserRecordUseCase.updatePromiseUserType(promiseId, userId, promiseUserType)
    }

    @Operation(summary = "내 위치 갱신하기", description = "내 위치를 갱신하고 최신화된 약속의 참여한 유저들의 위치를 반환합니다., (GRS84GEO(위경도) 단위로 요청하셔야 합니다")
    @PutMapping("/promises/{promise-id}/users/location")
    fun updateLocation(@PathVariable("promise-id") promiseId: Long, @RequestParam userLocation: CoordinateVo): List<PromiseLocationDto> {
        return promiseUserRecordUseCase.updateLocation(promiseId, userLocation)
    }
}
