package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseFindDto(
    val title: String,
    val address: String,
    val endTime: LocalDateTime,
    val userIds: List<UserInfoVo> = listOf(),
) {
    companion object {
        fun from(promise: Promise, List<String> url): PromiseFindDto {
            return PromiseFindDto(promise.title, promise.meetPlace!!.address, promise.endTime, )
        }
    }
}

