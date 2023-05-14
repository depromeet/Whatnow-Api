package com.depromeet.whatnow.api.auth.usecase

import com.depromeet.whatnow.api.auth.dto.response.TokenAndUserResponse
import org.springframework.stereotype.Service

@Service
class LoginUseCase {
    fun execute(token: String): TokenAndUserResponse {
        TODO("Not yet implemented")
    }
}
