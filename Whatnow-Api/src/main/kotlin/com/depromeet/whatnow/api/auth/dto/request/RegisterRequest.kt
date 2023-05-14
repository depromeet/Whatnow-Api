package com.depromeet.whatnow.api.auth.dto.request

data class RegisterRequest(
    val email: String,
    val profileImage: String,
    val isDefaultImage: Boolean,
    val username: String,
)
