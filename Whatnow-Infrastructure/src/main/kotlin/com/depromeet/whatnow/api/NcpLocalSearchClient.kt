package com.depromeet.whatnow.api

import com.depromeet.whatnow.api.config.ncp.NcpConfig
import com.depromeet.whatnow.api.dto.NcpMapInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
@FeignClient(name = "NcpLocalSearchClient", url = "\${ncp.local.search-url}", configuration = [NcpConfig::class])
interface NcpLocalSearchClient {
    @GetMapping("/local.json")
    fun searchByKeyword(
        @RequestHeader("X-Naver-Client-Id") accessKey: String,
        @RequestHeader("X-Naver-Client-Secret") secretKey: String,
        @RequestParam query: String,
    ): NcpMapInfoResponse
}
