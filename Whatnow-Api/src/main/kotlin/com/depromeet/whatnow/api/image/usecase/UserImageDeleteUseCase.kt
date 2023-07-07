package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.image.service.ImageDomainService

@UseCase
class UserImageDeleteUseCase(
    val imageDomainService: ImageDomainService,
) {
    fun execute(imageKey: String) {
        val userId = SecurityUtils.currentUserId
        imageDomainService.deleteForUser(userId, imageKey)
    }
}
