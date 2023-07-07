package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseFindDto(
    val promiseId: Long,
    val address: String,
    val coordinateVo: CoordinateVo,
    val title: String,
    val endTime: LocalDateTime,
    val users: List<UserInfoVo> = mutableListOf(),
) {
    companion object {
        fun of(promise: Promise, users: List<UserInfoVo>): PromiseFindDto {
            val userValues = mutableListOf<UserInfoVo>()
            userValues.addAll(users)
            return PromiseFindDto(
                promiseId = promise.id!!,
                address = promise.meetPlace!!.address,
                coordinateVo = promise.meetPlace!!.coordinate,
                title = promise.title,
                endTime = promise.endTime,
                users = userValues,
            )
        }
    }
}
