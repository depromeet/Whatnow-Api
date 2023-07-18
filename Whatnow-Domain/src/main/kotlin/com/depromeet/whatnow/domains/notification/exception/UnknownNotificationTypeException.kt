package com.depromeet.whatnow.domains.notification.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UnknownNotificationTypeException : WhatnowCodeException(
    NotificationErrorCode.UNKNOWN_NOTIFICATION_TYPE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UnknownNotificationTypeException()
    }
}
