package com.depromeet.whatnow.domains.notification.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class NotHighlightsTypeException : WhatnowCodeException(
    NotificationErrorCode.NOT_HIGH_LIGHTS_TYPE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = NotHighlightsTypeException()
    }
}
