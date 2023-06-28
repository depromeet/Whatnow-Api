package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.domain.User

data class PromiseUserInfoVo(
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean,
    val promiseUserType: PromiseUserType,
    // TODO : Interaction 리스트 ( ex. POOP : 1, MUSIC : 2, ... )
    // val interactions: List<Interaction>
) {
    //    interaction 기능 추가시 함께 추가할게요.
    companion object {
        fun of(user: User, promiseUserType: PromiseUserType): PromiseUserInfoVo {
            return PromiseUserInfoVo(user.profileImg, user.nickname, user.isDefaultImg, promiseUserType)
        }
    }
}
