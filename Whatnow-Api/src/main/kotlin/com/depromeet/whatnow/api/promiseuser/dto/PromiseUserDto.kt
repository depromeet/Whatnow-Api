package com.depromeet.whatnow.api.promiseuser.dto

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType

data class PromiseUserDto(
    val promiseId: Long,
    val mainUserId: Long,
    val userLocation: CoordinateVo?,
    val promiseUserType: PromiseUserType,
    val promiseProgress: PromiseProgress,
) {
    companion object {
        fun of(p: PromiseUser, progress: PromiseProgress): PromiseUserDto {
            return PromiseUserDto(p.promiseId, p.userId, p.userLocation, p.promiseUserType!!, progress)
        }
    }
}
