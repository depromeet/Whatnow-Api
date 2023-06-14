package com.depromeet.whatnow.api.auth.dto.request

data class RegisterRequest(
    val profileImage: String,
    val isDefaultImage: Boolean,
    val nickname: String,
    val fcmToken: String,
    val appAlarm: Boolean,
)
