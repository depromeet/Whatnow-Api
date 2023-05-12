package com.depromeet.whatnow.domains.user.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.events.domainEvent.UserSignUpEvent
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_user")
class User(

    @Embedded
    var OauthProfile: OauthProfile,

    var nickname: String,

    var profileImg: String, // 프로필 이미지도 vo 로 빼면 더 이쁠듯
    var isDefaultImg: Boolean,

    var lastLogin: LocalDateTime = LocalDateTime.now(),

    var fcmToken: String = "", // vo 로 빼서 알림 수신여부 까지 같이 관리하면 그림이 더 이쁠듯 합니다.

    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.NORMAL,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    val id: Long? = null,
) : BaseTimeEntity() {

    //    @PostPersist
    // 원래 되어야하는데.. postpersist가 콜백이라서 그런지 아예 다른 스레드에서 돌아버리네요..
    fun registerEvent() {
        Events.raise(UserSignUpEvent(this.id!!))
    }
}
