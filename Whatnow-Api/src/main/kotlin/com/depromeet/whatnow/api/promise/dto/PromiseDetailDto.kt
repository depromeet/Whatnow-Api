package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.api.notification.dto.HighlightsResponse
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
    val promiseImageUrls: List<String>?,
    val timeOverLocations: List<LocationCapture>,
    val highlights: HighlightsResponse,
) {
    companion object {
        fun of(
            promise: Promise,
            promiseUsers: List<PromiseUserInfoVo>,
            promiseImageUrls: List<String>,
            timeOverLocations: List<LocationCapture>,
            highlights: HighlightsResponse,
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
                highlights = highlights,
            )
        }
    }
}
