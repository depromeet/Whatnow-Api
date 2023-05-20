package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import feign.Response

@JsonNaming(SnakeCaseStrategy::class)
data class KakaoKauthErrorResponse(
    var error: String,
    var errorCode: String,
    var errorDescription: String,
) {

    companion object {
        fun from(response: Response): KakaoKauthErrorResponse {
            response.body().asInputStream().use { bodyIs ->
                val mapper = jacksonObjectMapper() // for kotlin support
                return mapper.readValue(bodyIs, KakaoKauthErrorResponse::class.java)
            }
        }
    }
}
