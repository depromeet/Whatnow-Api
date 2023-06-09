package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseFindDto(
    val title: String,
    val address: String,
    val endTime: LocalDateTime,
    val users: List<UserInfoVo> = listOf(),
) {
    companion object {
        fun from(promise: Promise, users: List<UserInfoVo>): PromiseFindDto {
            val userValues = listOf<UserInfoVo>()
            for (user in users) {
                userValues.plus(user)
            }
            return PromiseFindDto(
                promise.title,
                promise.meetPlace!!.address,
                promise.endTime,
                userValues,
            )
        }
    }
}
