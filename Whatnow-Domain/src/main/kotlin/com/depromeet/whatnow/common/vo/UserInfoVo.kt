package com.depromeet.whatnow.common.vo

import com.depromeet.whatnow.domains.user.domain.User
import javax.persistence.Embeddable

class UserInfoVo(
    val id : Long,
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean,
) {
    companion object {
        fun from(user: User): UserInfoVo {
            return UserInfoVo(user.id!!,user.profileImg, user.nickname, user.isDefaultImg)
        }
    }
}