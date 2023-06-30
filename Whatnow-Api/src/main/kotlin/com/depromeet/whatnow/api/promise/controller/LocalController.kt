package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.usecase.GetDistrictUseCase
import com.depromeet.whatnow.api.dto.NcpMapInfoResponse
import com.depromeet.whatnow.api.location.helper.NcpHelper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
@Tag(name = "3.1 [약속-장소]")
//@SecurityRequirement(name = "access-token")
class LocalController(
    val getDistrictUseCase: GetDistrictUseCase,
    val ncpHelper: NcpHelper,
) {
    @Operation(summary = "약속 장소 검색", description = "약속 장소를 검색합니다.")
    @GetMapping("/location")
    fun searchLocal(@RequestParam(value = "location") addressKeyword: String): NcpMapInfoResponse {
        return ncpHelper.getLocalSearch(addressKeyword)
    }
    @Operation(summary = "행정동 조회", description = "좌표를 통해 행정동을 조회합니다.")
    @GetMapping("/district")
    fun getDistrict(
        @RequestParam coordinateX: Double,
        @RequestParam coordinateY: Double,
    ): String {
        return getDistrictUseCase.execute(coordinateX, coordinateY)
    }
}
