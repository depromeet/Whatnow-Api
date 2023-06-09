package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseDetailDto(
    val promiseId: Long,
    val address: String,
    val coordinateVo: CoordinateVo,
    val title: String,
    val endTime: LocalDateTime,
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
                promiseId = promise.id!!,
                address = promise.meetPlace!!.address,
                coordinateVo = promise.meetPlace!!.coordinate,
                title = promise.title,
                endTime = promise.endTime,
                promiseUsers = promiseUsers,
                promiseImageUrls = promiseImageUrls,
                timeOverLocations = timeOverLocations,
            )
        }
    }
}
