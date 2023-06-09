package com.depromeet.whatnow.domains.user.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.common.vo.UserDetailVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.consts.USER_DEFAULT_PROFILE_IMAGE
import com.depromeet.whatnow.domains.user.exception.AlreadyDeletedUserException
import com.depromeet.whatnow.domains.user.exception.ForbiddenUserException
import com.depromeet.whatnow.events.domainEvent.UserProfileImageUpdatedEvent
import com.depromeet.whatnow.events.domainEvent.UserRegisterEvent
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.Table

@Entity
@Table(name = "tbl_user")
class User(

    @Embedded
    var oauthInfo: OauthInfo,

    var nickname: String,

    var profileImg: String, // 프로필 이미지도 vo 로 빼면 더 이쁠듯
    var isDefaultImg: Boolean,

    @Embedded
    var fcmNotification: FcmNotificationVo,

    var lastLoginAt: LocalDateTime = LocalDateTime.now(),

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.NORMAL,

    @Enumerated(EnumType.STRING)
    var accountRole: AccountRole = AccountRole.USER,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,
) : BaseTimeEntity() {

    @PostPersist
    fun registerEvent() {
        Events.raise(UserRegisterEvent(this.id!!))
    }

    fun login(fcmToken: String) {
        if (status != UserStatus.NORMAL) {
            throw ForbiddenUserException.EXCEPTION
        }
        lastLoginAt = LocalDateTime.now()
        updateToken(fcmToken) // 로그인시에 토큰 업데이트
    }

    fun withDraw() {
        if (status == UserStatus.DELETED) {
            throw AlreadyDeletedUserException.EXCEPTION
        }
        status = UserStatus.DELETED
        nickname = "탈퇴유저"
        profileImg = ""
        fcmNotification = FcmNotificationVo.disableAlarm(this.fcmNotification)
        oauthInfo = oauthInfo.withDrawOauthInfo()
    }

    private fun updateToken(fcmToken: String) {
        fcmNotification = FcmNotificationVo.updateToken(this.fcmNotification, fcmToken)
    }

    fun refresh() {
        if (status != UserStatus.NORMAL) {
            throw ForbiddenUserException.EXCEPTION
        }
        lastLoginAt = LocalDateTime.now()
    }

    fun toggleAppAlarmState() {
        fcmNotification = FcmNotificationVo.toggleAlarm(fcmNotification)
    }

    fun updateFcmToken(fcmToken: String) {
        fcmNotification = FcmNotificationVo.updateToken(fcmNotification, fcmToken)
    }

    fun updateProfile(profileImage: String, username: String, isDefaultImage: Boolean, imageKey: String) {
        this.nickname = username
        this.isDefaultImg = isDefaultImage
        this.profileImg = profileImage.takeIf { !isDefaultImage } ?: USER_DEFAULT_PROFILE_IMAGE

        if (!isDefaultImage) {
            Events.raise(UserProfileImageUpdatedEvent(this.id!!, imageKey))
        }
    }

    fun toUserInfoVo(): UserInfoVo {
        return UserInfoVo.from(this)
    }

    fun toUserDetailVo(): UserDetailVo {
        return UserDetailVo.from(this)
    }
}
