package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDomainService(
    val userRepository: UserRepository,
) {

    @Transactional
    fun registerUser(): User {
        val newUser = User(OauthInfo("테스트", OauthProvider.KAKAO), "테스트", "테스트", false)
        userRepository.save(newUser)
        newUser.registerEvent()
        return newUser
    }

    fun upsertUser(
        username: String,
        profileImage: String,
        defaultImage: Boolean,
        toOauthInfo: OauthInfo,
    ): User {
        TODO("Not yet implemented")
    }
}
