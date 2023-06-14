package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.annotation.DomainService
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.domain.FcmNotificationVo
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.exception.AlreadySignUpUserException
import org.springframework.transaction.annotation.Transactional

@DomainService
class UserDomainService(
    val userAdapter: UserAdapter,
) {

    /**
     * https://toss.tech/article/kotlin-result
     * 레포지토리에서 가져오던걸 어떻게 리팩토링 할 까 고민하다가 좋은 글이 있어서 공유합니다.
     * 기본으로 query 메소드를 userAdapter로 놓고.
     * Result 로 한번 래핑을 한뒤에, onSuccess,onFailure , getOrElse 등의 메소드를 이용해서
     * 적절하게 대응을 할 수 있습니다. 매우 깔끔스
     */
    fun resultQueryByOauthInfo(oauthInfo: OauthInfo): Result<User> {
        return runCatching {
            userAdapter.queryByOauthInfo(oauthInfo)
        }
    }

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
        return resultQueryByOauthInfo(oauthInfo).getOrElse {
            val newUser = userAdapter.save(User(oauthInfo, username, profileImage, defaultImage, FcmNotificationVo("", false)))
            newUser.registerEvent()
            return newUser
        }
    }

    fun checkUserCanRegister(oauthInfo: OauthInfo): Boolean {
        return resultQueryByOauthInfo(oauthInfo).isFailure // 유저가 없으면 회원가입 가능
    }

    fun registerUser(username: String, profileImage: String, defaultImage: Boolean, oauthInfo: OauthInfo, oauthId: String, fcmToken: String, appAlarm: Boolean): User {
        return resultQueryByOauthInfo(oauthInfo).onSuccess { // 계정이 있는 경우엔 exception 발생
            throw AlreadySignUpUserException.EXCEPTION
        }.getOrElse {
            userAdapter.save(
                User(
                    oauthInfo,
                    username,
                    profileImage,
                    defaultImage,
                    FcmNotificationVo(fcmToken, appAlarm),
                ),
            )
        }
    }

    @Transactional
    fun loginUser(oauthInfo: OauthInfo, fcmToken: String): User {
        val user = userAdapter.queryByOauthInfo(oauthInfo)
        user.login(fcmToken)
        return user
    }

    /**
     * 유저가 리프레쉬 할때 ( 클라이언트와 fcm 토큰 업데이트는 로그인 회원가입 할 때만 하기로 합의 )
     */
    @Transactional
    fun refreshUser(oauthInfo: OauthInfo): User {
        val user = userAdapter.queryByOauthInfo(oauthInfo)
        user.refresh()
        return user
    }

    @Transactional
    fun withDrawUser(currentUserId: Long) {
        val user = userAdapter.queryUser(currentUserId)
        user.withDraw()
    }

    @Transactional
    fun toggleAppAlarmState(currentUserId: Long): User {
        val user = userAdapter.queryUser(currentUserId)
        user.toggleAppAlarmState()
        return user
    }

    @Transactional
    fun updateFcmToken(currentUserId: Long, fcmToken: String): User {
        val user = userAdapter.queryUser(currentUserId)
        user.updateFcmToken(fcmToken)
        return user
    }

    @Transactional
    fun updateProfile(currentUserId: Long, profileImage: String, username: String): User {
        val user = userAdapter.queryUser(currentUserId)
        user.updateProfile(profileImage, username)
        return user
    }
}
