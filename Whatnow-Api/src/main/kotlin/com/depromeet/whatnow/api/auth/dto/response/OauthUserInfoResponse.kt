package com.depromeet.whatnow.api.auth.dto.response

import com.depromeet.whatnow.api.auth.usecase.helper.OauthUserInfoDto

data class OauthUserInfoResponse(
    val email: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
) {

    companion object {
        fun from(oauthUserInfoDto: OauthUserInfoDto): OauthUserInfoResponse {
            return OauthUserInfoResponse(oauthUserInfoDto.email, oauthUserInfoDto.profileImage, oauthUserInfoDto.isDefaultImage, oauthUserInfoDto.username)
        }
    }
}
