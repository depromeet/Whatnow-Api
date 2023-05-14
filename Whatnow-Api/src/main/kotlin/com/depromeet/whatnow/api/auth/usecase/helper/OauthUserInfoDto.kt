package com.depromeet.whatnow.api.auth.usecase.helper

import com.depromeet.whatnow.domains.user.domain.OauthProvider

data class OauthUserInfoDto(
    val oauthId: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
    val oauthProvider: OauthProvider,
)
