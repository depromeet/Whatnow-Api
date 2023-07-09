package com.depromeet.whatnow.api.location.helper

import com.depromeet.whatnow.annotation.Helper
import com.depromeet.whatnow.api.NcpLocalSearchClient
import com.depromeet.whatnow.api.dto.NcpMapInfoResponse
import com.depromeet.whatnow.config.NcpProperties
import com.depromeet.whatnow.consts.NCP_LOCAL_SEARCH_DISPLAY_COUNT

@Helper
class NcpHelper(
    val ncpClient: NcpLocalSearchClient,
    val ncpProperties: NcpProperties,
) {
    var ncpPropertie = ncpProperties.local

    // 검색 키워드 조회
    fun getLocalSearch(keyword: String): NcpMapInfoResponse {
        return ncpClient.searchByKeyword(ncpPropertie.accessKey, ncpPropertie.secretKey, NCP_LOCAL_SEARCH_DISPLAY_COUNT, keyword)
    }
}
