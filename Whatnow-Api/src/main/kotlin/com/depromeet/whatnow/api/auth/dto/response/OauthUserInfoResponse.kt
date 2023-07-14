package com.depromeet.whatnow.api.auth.dto.response

import com.depromeet.whatnow.api.auth.helper.OauthUserInfoDto

data class OauthUserInfoResponse(
    val profileImage: String,
    val isDefaultImage: Boolean,
    val nickname: String,
) {

    companion object {
        fun from(oauthUserInfoDto: OauthUserInfoDto): OauthUserInfoResponse {
            return OauthUserInfoResponse(oauthUserInfoDto.profileImage, oauthUserInfoDto.isDefaultImage, oauthUserInfoDto.username)
        }
    }
}
