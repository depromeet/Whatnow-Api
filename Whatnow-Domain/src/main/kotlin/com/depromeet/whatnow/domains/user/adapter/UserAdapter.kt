package com.depromeet.whatnow.domains.user.adapter

import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.exception.UserNotFoundException
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserAdapter(
    val userRepository: UserRepository,
) {

    fun queryUser(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: run { throw UserNotFoundException.EXCEPTION }
    }
}
