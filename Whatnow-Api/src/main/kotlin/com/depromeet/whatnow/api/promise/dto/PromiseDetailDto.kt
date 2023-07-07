package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseDetailDto(
    val title: String,
    val date: LocalDateTime,
    // 유저의 마지막 위치 리스트
    val promiseUsers: List<PromiseUserInfoVo>,
    // TODO : 약속 기록 사진 기능 추가시 함께 추가할게요.
    val promiseImageUrls: List<String>?,
    val timeOverLocations: List<LocationCapture>,
    // TODO : highlight 기능 추가시 함께 추가할게요. ( 최대 3개 제한 )
// x   val highlights: List<NotificationDto>,
) {
    companion object {
        fun of(
            promise: Promise,
            promiseUsers: List<PromiseUserInfoVo>,
            promiseImageUrls: List<String>,
            timeOverLocations: List<LocationCapture>,
        ): PromiseDetailDto {
            return PromiseDetailDto(
                title = promise.title,
                date = promise.endTime,
                promiseUsers = promiseUsers,
                promiseImageUrls = promiseImageUrls,
                timeOverLocations = timeOverLocations,
            )
        }
    }
}
