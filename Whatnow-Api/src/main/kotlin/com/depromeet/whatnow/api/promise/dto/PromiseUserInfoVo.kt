package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.api.interaction.dto.InteractionDto
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.user.domain.User

data class PromiseUserInfoVo(
    val profileImg: String,
    val nickname: String,
    val isDefaultImg: Boolean,
    val promiseUserType: PromiseUserType,
    val interactions: List<InteractionDto>,
) {
    companion object {
        fun of(user: User, promiseUserType: PromiseUserType, interactionDtoList: List<InteractionDto>): PromiseUserInfoVo {
            val list = interactionDtoList.sortedByDescending { interactionDto -> interactionDto.count }
            return PromiseUserInfoVo(user.profileImg, user.nickname, user.isDefaultImg, promiseUserType, list)
        }
    }
}
