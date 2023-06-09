package com.depromeet.whatnow.common.vo

import com.depromeet.whatnow.domains.user.domain.User
import javax.persistence.Embeddable

@Embeddable
class UserInfoVo(
    var profileImg: String?,
    var nickname: String,
    var isDefaultImg: Boolean,
) {
    companion object {
        fun from(user: User): UserInfoVo {
            return UserInfoVo(user.profileImg, user.nickname, user.isDefaultImg)
        }
    }
}
