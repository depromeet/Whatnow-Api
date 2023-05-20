package com.depromeet.whatnow.api.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoInformationResponse(
//    val properties: Properties,
    val id: String,
    val kakaoAccount: KakaoAccount,
) {

//    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
//    data class Properties(
//        val nickname: String,
//    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class KakaoAccount(
        val profile: Profile,
        val email: String?,
    ) {

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
        data class Profile(
            val nickname: String,
            val profileImageUrl: String,
            val isDefaultImage: Boolean,
        )
    }
}
