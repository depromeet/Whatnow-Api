package com.depromeet.whatnow.api.user.controller

import com.depromeet.whatnow.api.user.dto.request.UpdateFcmTokenRequest
import com.depromeet.whatnow.api.user.dto.request.UpdateProfileRequest
import com.depromeet.whatnow.api.user.usecase.ReadUserUseCase
import com.depromeet.whatnow.api.user.usecase.UpdateUserUseCase
import com.depromeet.whatnow.common.vo.UserDetailVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/v1/users")
@Tag(name = "2. [유저]")
@SecurityRequirement(name = "access-token")
class UserController(
    val readUserUseCase: ReadUserUseCase,
    val updateUserUseCase: UpdateUserUseCase,
) {

    @GetMapping("/me")
    @Operation(summary = "자신의 유저 디테일 정보를 가져옵니다.")
    fun getMyUserInfo(): UserDetailVo {
        return readUserUseCase.findMyInfo()
    }

    @GetMapping("/{userId}")
    @Operation(summary = "다른 사람의 유저 정보를 가져옵니다.")
    fun getOtherUserInfo(@PathVariable userId: Long): UserInfoVo {
        return readUserUseCase.findUserById(userId)
    }

    @PatchMapping("/alarm")
    @Operation(summary = "내 앱 알림 허용 정보를 토글링 합니다")
    fun toggleAppAlarmState(): UserDetailVo {
        return updateUserUseCase.toggleAppAlarmState()
    }

    @PatchMapping("/fcm-token")
    @Operation(summary = "fcm 토큰정보를 업데이트 합니다")
    fun updateFcmToken(
        @Valid @RequestBody
        updateFcmTokenRequest: UpdateFcmTokenRequest,
    ): UserDetailVo {
        return updateUserUseCase.updateFcmToken(updateFcmTokenRequest)
    }

    @PatchMapping("/profile")
    fun updateProfile(
        @Valid @RequestBody
        updateProfileRequest: UpdateProfileRequest,
    ): UserDetailVo {
        return updateUserUseCase.updateProfile(updateProfileRequest)
    }
}
