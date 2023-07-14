package com.depromeet.whatnow.api.promiseuser.dto

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType

data class PromiseLocationDto(
    val userId: Long,
    val userLocation: CoordinateVo,
    val promiseUserType: PromiseUserType,
) {
    companion object {
        fun from(promiseUser: PromiseUser): PromiseLocationDto {
            return PromiseLocationDto(
                userId = promiseUser.userId,
                userLocation = promiseUser.userLocation,
                promiseUserType = promiseUser.promiseUserType,
            )
        }
    }
}
