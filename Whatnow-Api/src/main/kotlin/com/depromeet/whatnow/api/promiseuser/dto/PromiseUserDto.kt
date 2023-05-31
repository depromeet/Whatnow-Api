package com.depromeet.whatnow.api.promiseuser.dto

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType

data class PromiseUserDto(
    val promiseId: Long,
    val userId: Long,
    val userLocation: CoordinateVo?,
    val promiseUserType: PromiseUserType,
) {
    companion object {
        fun from(p: PromiseUser): PromiseUserDto {
            return PromiseUserDto(p.promiseId, p.userId, p.userLocation, p.promiseUserType!!)
        }
    }
}
