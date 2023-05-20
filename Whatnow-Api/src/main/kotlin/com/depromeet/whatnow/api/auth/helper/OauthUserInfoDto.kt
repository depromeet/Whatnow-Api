package com.depromeet.whatnow.api.auth.helper

import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider

data class OauthUserInfoDto(
    val oauthId: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
    val email: String?,
    val oauthProvider: OauthProvider,
) {
    fun toOauthInfo(): OauthInfo {
        return OauthInfo(oauthId, oauthProvider)
    }
}
