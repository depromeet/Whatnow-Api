package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.user.dto.request.UpdateFcmTokenRequest
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.service.UserDomainService

@UseCase
class UpdateUserUseCase(
    val userDomainService: UserDomainService,
) {
    fun toggleAppAlarmState(): User {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userDomainService.toggleAppAlarmState(currentUserId)
    }

    fun updateFcmToken(updateTokenRequest: UpdateFcmTokenRequest): User {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userDomainService.updateFcmToken(currentUserId, updateTokenRequest.fcmToken)
    }
}
