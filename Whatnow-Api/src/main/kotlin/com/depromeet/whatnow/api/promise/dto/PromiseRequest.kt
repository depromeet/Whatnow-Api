package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.PlaceVo
import java.time.LocalDateTime

data class PromiseRequest(
    val title: String,
    val mainUserId: Long,
    val meetPlace: PlaceVo,
    val endTime: LocalDateTime,
)
