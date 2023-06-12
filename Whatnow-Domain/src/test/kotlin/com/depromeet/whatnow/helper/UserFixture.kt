package com.depromeet.whatnow.helper

import com.depromeet.whatnow.domains.user.domain.AccountRole
import com.depromeet.whatnow.domains.user.domain.FcmNotificationVo
import com.depromeet.whatnow.domains.user.domain.OauthInfo
import com.depromeet.whatnow.domains.user.domain.OauthProvider
import com.depromeet.whatnow.domains.user.domain.User
import com.depromeet.whatnow.domains.user.domain.UserStatus
import java.time.LocalDateTime

fun user_id_1_fixture(): User {
    return User(
        oauthInfo_kakao_1_fixture(),
        "유저1",
        "url1",
        true,
        fcmNotificationVo_fixture(),
        LocalDateTime.now(),
        UserStatus.NORMAL,
        AccountRole.USER,
        1,
    )
}

fun oauthInfo_kakao_1_fixture(): OauthInfo {
    return OauthInfo("1", OauthProvider.KAKAO)
}

fun fcmNotificationVo_fixture(): FcmNotificationVo {
    return FcmNotificationVo("fcmToken", true)
}
