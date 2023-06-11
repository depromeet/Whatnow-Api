package com.depromeet.whatnow.domains.user.domain

import javax.persistence.Embeddable

@Embeddable
class FcmNotificationVo(
    var fcmToken: String = "",

    var appAlarm: Boolean,
) {

    companion object {
        fun toggleAlarm(originalState: FcmNotificationVo): FcmNotificationVo {
            return FcmNotificationVo(originalState.fcmToken, originalState.appAlarm.not())
        }
        fun disableAlarm(originalState: FcmNotificationVo): FcmNotificationVo {
            return FcmNotificationVo(originalState.fcmToken, false)
        }

        fun deleteToken(originalState: FcmNotificationVo): FcmNotificationVo {
            return FcmNotificationVo("", originalState.appAlarm)
        }

        fun updateToken(originalState: FcmNotificationVo, fcmToken: String): FcmNotificationVo {
            return FcmNotificationVo(fcmToken, originalState.appAlarm)
        }
    }
}
