package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.dto.NcpMapInfoResponse
import com.depromeet.whatnow.api.location.helper.NcpHelper
import com.depromeet.whatnow.api.promise.usecase.GetDistrictUseCase
import com.depromeet.whatnow.domains.district.domain.District
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class LocalControllerTest {

    @Mock
    private lateinit var getDistrictUseCase: GetDistrictUseCase

    @Mock
    private lateinit var ncpHelper: NcpHelper

    private lateinit var localController: LocalController

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        localController = LocalController(getDistrictUseCase, ncpHelper)
    }

    @Test
    fun `searchLocal should return NcpMapInfoResponse`() {
        // Given
        val addressKeyword = "New York"
        val expectedResponse = NcpMapInfoResponse(
            lastBuildDate = "2022:02:02",
            total = 1,
            start = 1,
            display = 1,
            items = listOf(),
        )
        `when`(ncpHelper.getLocalSearch(addressKeyword)).thenReturn(expectedResponse)

        // When
        val result = localController.searchLocal(addressKeyword)

        // Then
        assertEquals(expectedResponse, result)
    }

    @Test
    fun `행정동을 조회할 수 있다`() {
        // Given
        val coordinateX = 123.4
        val coordinateY = 432.23
        val expectedDistrictName = "서울특별시 종로구 평창동"
        val expectedDistrict = District(
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
        `when`(getDistrictUseCase.execute(coordinateX, coordinateY)).thenReturn(expectedDistrict.properties.adm_nm)

        // When
        val result = localController.getDistrict(coordinateX, coordinateY)

        // Then
        assertEquals(expectedDistrict.properties.adm_nm, result)
    }
}
