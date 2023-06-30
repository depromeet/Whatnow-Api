package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.domains.district.repository.DistrictRepository
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.Position

@UseCase
class GetDistrictUseCase(
    val districtRepository: DistrictRepository
) {
    fun execute(coordinateX: Double, coordinateY: Double): String {
        val intersects =
            districtRepository.findByLocationIntersects(Point(Position(coordinateX, coordinateY)))
        return intersects.properties.adm_nm
    }
}
