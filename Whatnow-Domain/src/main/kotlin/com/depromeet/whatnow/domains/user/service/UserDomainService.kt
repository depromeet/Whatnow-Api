package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.domain.OauthProfile
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserDomainService(
    val userRepository: UserRepository,
) {

    fun registerUser(): User {
        var newUser = User(OauthProfile("테스트", OauthProvider.KAKAO), "테스트", "테스트", false)
        userRepository.save(newUser)
        return newUser
    }
}
