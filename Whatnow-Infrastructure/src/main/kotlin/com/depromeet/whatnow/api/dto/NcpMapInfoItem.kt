package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(SnakeCaseStrategy::class)
class NcpMapInfoItem(
    val title: String? = null,
    val link: String? = null,
    val category: String? = null,
    val description: String? = null,
    val telephone: String? = null,
    val address: String? = null,
    val roadAddress: String? = null,
    val mapx: String? = null,
    val mapy: String? = null,
)
