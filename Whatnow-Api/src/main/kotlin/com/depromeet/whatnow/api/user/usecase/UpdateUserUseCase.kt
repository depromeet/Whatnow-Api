package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.user.dto.request.UpdateFcmTokenRequest
import com.depromeet.whatnow.api.user.dto.request.UpdateProfileRequest
import com.depromeet.whatnow.common.vo.UserDetailVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.service.UserDomainService

@UseCase
class UpdateUserUseCase(
    val userDomainService: UserDomainService,
) {
    fun toggleAppAlarmState(): UserDetailVo {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userDomainService.toggleAppAlarmState(currentUserId).toUserDetailVo()
    }

    fun updateFcmToken(updateTokenRequest: UpdateFcmTokenRequest): UserDetailVo {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userDomainService.updateFcmToken(currentUserId, updateTokenRequest.fcmToken).toUserDetailVo()
    }

    fun updateProfile(updateProfileRequest: UpdateProfileRequest): UserDetailVo {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userDomainService.updateProfile(currentUserId, updateProfileRequest.profileImage, updateProfileRequest.username)
            .toUserDetailVo()
    }
}
