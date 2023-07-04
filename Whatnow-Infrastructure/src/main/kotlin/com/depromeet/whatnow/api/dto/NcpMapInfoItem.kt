package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
class NcpMapInfoItem(
    val title: String,
    val link: String,
    val category: String,
    val description: String,
    val telephone: String,
    val address: String,
    val roadAddress: String ? = "",
    val mapx: Double,
    val mapy: Double,
) {
    companion object {
        fun from(ncpMapInfoResponse: NcpMapInfoItem): NcpMapInfoItem {
            return NcpMapInfoItem(
                title = ncpMapInfoResponse.title,
                link = ncpMapInfoResponse.link,
                category = ncpMapInfoResponse.category,
                description = ncpMapInfoResponse.description,
                telephone = ncpMapInfoResponse.telephone,
                address = ncpMapInfoResponse.address,
                roadAddress = ncpMapInfoResponse.roadAddress,
                mapx = ncpMapInfoResponse.mapx,
                mapy = ncpMapInfoResponse.mapy,
            )
        }
    }
}
