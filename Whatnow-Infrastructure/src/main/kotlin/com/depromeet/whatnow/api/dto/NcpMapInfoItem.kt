package com.depromeet.whatnow.api.dto

import com.depromeet.whatnow.api.utils.GeoTrans
import com.depromeet.whatnow.api.utils.GeoTrans.convert
import com.depromeet.whatnow.api.utils.GeoTransPoint
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
            val convertKATECtoGeo = convertKATECtoGeo(ncpMapInfoResponse.mapx, ncpMapInfoResponse.mapy)

            return NcpMapInfoItem(
                title = ncpMapInfoResponse.title,
                link = ncpMapInfoResponse.link,
                category = ncpMapInfoResponse.category,
                description = ncpMapInfoResponse.description,
                telephone = ncpMapInfoResponse.telephone,
                address = ncpMapInfoResponse.address,
                roadAddress = ncpMapInfoResponse.roadAddress,
                mapx = convertKATECtoGeo.x,
                mapy = convertKATECtoGeo.y,
            )
        }

        private fun convertKATECtoGeo(coordinateX: Double, coordinateY: Double): GeoTransPoint {
            val katecPt: GeoTransPoint = GeoTransPoint(coordinateX, coordinateY)
            return convert(GeoTrans.KATEC, GeoTrans.GEO, katecPt)
        }
    }
}
