package com.depromeet.whatnow.api.promise.controller

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
@SecurityRequirement(name = "access-token")
class LocalController(
    val ncpHelper: NcpHelper,
) {
    @Operation(summary = "약속 장소 검색", description = "약속 장소를 검색합니다.")
    @GetMapping("/location")
    fun searchLocal(@RequestParam(value = "location") addressKeyword: String): NcpMapInfoResponse {
        return ncpHelper.getLocalSearch(addressKeyword)
    }
}
