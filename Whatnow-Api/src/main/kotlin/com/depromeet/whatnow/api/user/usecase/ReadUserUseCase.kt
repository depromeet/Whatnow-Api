package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull

@UseCase
class ReadUserUseCase(
    val userRepository: UserRepository,
) {
    fun findUser(): User {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userRepository.findByIdOrNull(currentUserId)!!
    }
}
