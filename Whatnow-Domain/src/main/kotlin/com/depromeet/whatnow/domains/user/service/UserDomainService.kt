package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.domains.user.domain.FcmNotificationVo
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.exception.AlreadySignUpUserException
import com.depromeet.whatnow.domains.user.exception.UserNotFoundException
import com.depromeet.whatnow.domains.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.transaction.annotation.Transactional

@DomainService
class UserDomainService(
    val userRepository: UserRepository,
) {

    /**
     * 백엔드 개발용 회원가입 메서드
     * upsert로 동작합니다.
     */
    @Transactional
    fun upsertUser(
        username: String,
        profileImage: String,
        defaultImage: Boolean,
        oauthInfo: OauthInfo,
        oauthId: String,
    ): User {
        return userRepository.findByOauthInfo(oauthInfo) ?: run {
            val newUser = userRepository.save(User(oauthInfo, username, profileImage, defaultImage, FcmNotificationVo("", false)))
            newUser.registerEvent()
            return newUser
        }
    }

    fun checkUserCanRegister(oauthInfo: OauthInfo): Boolean {
        userRepository.findByOauthInfo(oauthInfo)?. let { return false }
        return true
    }

    fun registerUser(username: String, profileImage: String, defaultImage: Boolean, oauthInfo: OauthInfo, oauthId: String, fcmToken: String, appAlarm: Boolean): User {
        userRepository.findByOauthInfo(oauthInfo)?. let { throw AlreadySignUpUserException.EXCEPTION }
        return userRepository.save(
            User(
                oauthInfo,
                username,
                profileImage,
                defaultImage,
                FcmNotificationVo(fcmToken, appAlarm),
            ),
        )
    }

    @Transactional
    fun loginUser(oauthInfo: OauthInfo, fcmToken: String): User {
        val user = userRepository.findByOauthInfo(oauthInfo) ?: run { throw UserNotFoundException.EXCEPTION }
        user.login(fcmToken)
        return user
    }

    /**
     * 유저가 리프레쉬 할때 ( 클라이언트와 fcm 토큰 업데이트는 로그인 회원가입 할 때만 하기로 합의 )
     */
    @Transactional
    fun refreshUser(oauthInfo: OauthInfo): User {
        val user = userRepository.findByOauthInfo(oauthInfo) ?: run { throw UserNotFoundException.EXCEPTION }
        user.refresh()
        return user
    }

    @Transactional
    fun withDrawUser(currentUserId: Long) {
        val user = userRepository.findByIdOrNull(currentUserId) ?: run { throw UserNotFoundException.EXCEPTION }
        user.withDraw()
    }
}
