package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserDomainService(
    val userRepository: UserRepository,
) {

    /**
     * 백엔드 개발용 회원가입 메서드
     * upsert로 동작합니다.
     */
    fun upsertUser(
        username: String,
        profileImage: String,
        defaultImage: Boolean,
        oauthInfo: OauthInfo,
        oauthId: String,
    ): User {
        return userRepository.findByOauthInfo(oauthInfo) ?: userRepository.save(User(oauthInfo, username, profileImage, defaultImage))
    }

    fun checkUserCanRegister(oauthInfo: OauthInfo): Boolean {
        userRepository.findByOauthInfo(oauthInfo)?. let { return false }
        return true
    }

    fun registerUser(username: String, profileImage: String, defaultImage: Boolean, oauthInfo: OauthInfo, oauthId: String): User {
        userRepository.findByOauthInfo(oauthInfo)?. let { throw Error("이미 회원가입했으면 안됨 ( 나중에 비즈니스 에러로 교체 예정") }
        return userRepository.save(User(oauthInfo, username, profileImage, defaultImage))
    }
}
