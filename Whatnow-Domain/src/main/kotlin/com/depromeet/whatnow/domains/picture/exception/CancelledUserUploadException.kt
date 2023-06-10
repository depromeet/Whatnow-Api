package com.depromeet.whatnow.domains.picture.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class CancelledUserUploadException: WhatnowCodeException(
    PictureErrorCode.CANCELLED_USER_UPLOAD,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = CancelledUserUploadException()
    }
}
