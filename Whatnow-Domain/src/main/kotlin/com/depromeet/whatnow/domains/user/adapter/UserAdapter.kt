package com.depromeet.whatnow.domains.user.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.exception.UserNotFoundException
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull

@Adapter
class UserAdapter(
    val userRepository: UserRepository,
) {

    fun queryUser(userId: Long): User {
        return userRepository.findByIdOrNull(userId) ?: run { throw UserNotFoundException.EXCEPTION }
    }

    fun queryByOauthInfo(oauthInfo: OauthInfo): User {
        return userRepository.findByOauthInfo(oauthInfo) ?: run { throw UserNotFoundException.EXCEPTION }
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }
}
