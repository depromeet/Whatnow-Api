package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.CoordinateVo

data class LocationCapture(
    val userId: Long,
    val coordinateVo: CoordinateVo,
) {
    companion object {
        fun of(userId: Long, coordinateVo: CoordinateVo): LocationCapture {
            return LocationCapture(userId, coordinateVo)
        }
    }
}
