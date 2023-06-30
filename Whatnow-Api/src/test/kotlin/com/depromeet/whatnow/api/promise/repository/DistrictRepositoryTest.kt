package com.depromeet.whatnow.api.promise.repository

import com.depromeet.whatnow.domains.district.domain.District
import com.depromeet.whatnow.domains.district.repository.DistrictRepository
import com.mongodb.client.model.geojson.Point
import com.mongodb.client.model.geojson.Position
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DistrictRepositoryTest {

    @Mock
    private lateinit var districtRepository: DistrictRepository

    @Test
    fun `xy좌표로 행정동 조회를 할 수 있다`() {
        // Given
        val coordinateX = 123.4
        val coordinateY = 432.23
        val expectedDistrictName = "서울특별시 종로구 평창동"
        val district = District(
            id = "1",
            type = "Feature",
            properties = District.Properties(
                OBJECTID = 1,
                adm_nm = expectedDistrictName,
                adm_cd = "1101056",
                adm_cd2 = "1111056000",
                sgg = "11110",
                sido = "11",
                sidonm = "서울특별시",
                sggnm = "종로구",
            ),
            geometry = District.Geometry(
                type = "MultiPolygon",
                coordinates = listOf(listOf(listOf(126.97507466788086, 37.63138628651299))),
            ),
        )

        `when`(
            districtRepository.findByLocationIntersects(
                Point(
                    Position(
                        coordinateX,
                        coordinateY,
                    ),
                ),
            ),
        ).thenReturn(district)
        // When
        val result = districtRepository.findByLocationIntersects(
            Point(
                Position(
                    coordinateX,
                    coordinateY,
                ),
            ),
        )

        // Then
        assertEquals(district, result)
    }
}
