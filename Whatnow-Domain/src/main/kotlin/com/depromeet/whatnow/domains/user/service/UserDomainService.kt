package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.domain.OauthProfile
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.stereotype.Service
<<<<<<< HEAD
=======
import org.springframework.transaction.annotation.Transactional
>>>>>>> develop

@Service
class UserDomainService(
    val userRepository: UserRepository,
) {

<<<<<<< HEAD
    fun registerUser(): User {
        var newUser = User(OauthProfile("테스트", OauthProvider.KAKAO), "테스트", "테스트", false)
        userRepository.save(newUser)
=======
    @Transactional
    fun registerUser(): User {
        val newUser = User(OauthProfile("테스트", OauthProvider.KAKAO), "테스트", "테스트", false)
        userRepository.save(newUser)
        newUser.registerEvent()
>>>>>>> develop
        return newUser
    }
}
