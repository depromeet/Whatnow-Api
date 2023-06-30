package com.depromeet.whatnow.api.district.controller

import com.depromeet.whatnow.api.district.usecase.GetDistrictUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "3-1. [행정동]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class DistrictController(
    val getDistrictUseCase: GetDistrictUseCase
) {
    @Operation(summary = "행정동 조회", description = "좌표를 통해 행정동을 조회합니다.")
    @GetMapping("/district")
    fun getDistrict(
        @RequestParam coordinateX: Double,
        @RequestParam coordinateY: Double,
    ): String {
        return getDistrictUseCase.execute(coordinateX, coordinateY)
    }
}