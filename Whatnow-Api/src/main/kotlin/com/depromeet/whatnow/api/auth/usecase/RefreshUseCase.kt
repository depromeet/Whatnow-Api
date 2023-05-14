package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import org.springframework.stereotype.Service

@Service
class RefreshUseCase {
    fun execute(refreshToken: String): TokenAndUserResponse {
        TODO("Not yet implemented")
    }
}
