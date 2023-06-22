package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
class NcpMapInfoResponse(
    val lastBuildDate: String? = null,
    val total: Int? = null,
    val start: Int? = null,
    val display: Int? = null,
    val items: List<NcpMapInfoItem>? = null,
)
