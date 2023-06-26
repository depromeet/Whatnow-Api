package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class CancelledUserUploadException : WhatnowCodeException(
    ImageErrorCode.CANCELLED_USER_UPLOAD,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = CancelledUserUploadException()
    }
}
