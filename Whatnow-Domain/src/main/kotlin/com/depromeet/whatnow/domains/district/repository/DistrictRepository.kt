package com.depromeet.whatnow.domains.district.repository

import com.depromeet.whatnow.domains.district.domain.District
import com.mongodb.client.model.geojson.Point
import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface DistrictRepository : ReactiveMongoRepository<District, String> {
    @Query("{\$geoIntersects: {\$geometry: {type: 'Point', coordinates: ?0}}}")
    fun findByLocationIntersects(coordinates: Point?): District
}
