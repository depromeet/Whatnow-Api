package com.depromeet.whatnow.api.user.usecase

import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ReadUserUseCase(
    val userRepository: UserRepository,
) {
    fun findUser(): User {
        val currentUserId: Long = SecurityUtils.currentUserId
        return userRepository.findByIdOrNull(currentUserId)!!
    }
}
