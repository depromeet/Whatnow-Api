package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
class NcpMapInfoResponse(
    val lastBuildDate: String ? = "",
    val total: Int,
    val start: Int,
    val display: Int,
    val items: List<NcpMapInfoItem>,
)
