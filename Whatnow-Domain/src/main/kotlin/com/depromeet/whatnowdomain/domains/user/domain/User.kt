package com.depromeet.whatnowdomain.domains.user.domain

import com.depromeet.whatnowdomain.common.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "user_id")
        val id : Long,

        @Embedded
        var OauthProfile : OauthProfile,

        var nickname : String,

        var profileImg : String,  // 프로필 이미지도 vo 로 빼면 더 이쁠듯
        var isDefaultImg : Boolean,

        var lastLogin : LocalDateTime,

        var fcmToken : String = "", // vo 로 빼서 알림 수신여부 까지 같이 관리하면 그림이 더 이쁠듯 합니다.

        @Enumerated(EnumType.STRING)
        var status : UserStatus = UserStatus.NORMAL,

        ) : BaseTimeEntity(){
}