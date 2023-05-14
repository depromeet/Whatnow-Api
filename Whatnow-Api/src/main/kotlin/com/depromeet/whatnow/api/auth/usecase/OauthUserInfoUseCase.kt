package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.OauthUserInfoResponse
import org.springframework.stereotype.Service

@Service
class OauthUserInfoUseCase {
    fun execute(accessToken: String): OauthUserInfoResponse {
        TODO("Not yet implemented")
    }
}
