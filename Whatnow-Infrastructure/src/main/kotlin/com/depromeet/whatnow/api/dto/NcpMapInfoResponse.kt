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
) {
    companion object {
        fun from(ncpMapInfoResponse: NcpMapInfoResponse): NcpMapInfoResponse {
            ncpMapInfoResponse.items.map { NcpMapInfoItem.from(it) }
            return NcpMapInfoResponse(
                lastBuildDate = ncpMapInfoResponse.lastBuildDate,
                total = ncpMapInfoResponse.total,
                start = ncpMapInfoResponse.start,
                display = ncpMapInfoResponse.display,
                items = ncpMapInfoResponse.items,
            )
        }
    }
}
