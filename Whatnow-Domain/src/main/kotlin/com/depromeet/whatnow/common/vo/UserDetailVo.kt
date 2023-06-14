package com.depromeet.whatnow.common.vo

import com.depromeet.whatnow.domains.user.domain.FcmNotificationVo
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.domain.User

class UserDetailVo(
    val id: Long,
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean,
    val oauthProvider: OauthProvider,
    val fcmInfo: FcmNotificationVo,
) {
    companion object {
        fun from(user: User): UserDetailVo {
            return UserDetailVo(
                user.id!!,
                user.profileImg,
                user.nickname,
                user.isDefaultImg,
                user.oauthInfo.oauthProvider,
                user.fcmNotification,
            )
        }
    }
}
