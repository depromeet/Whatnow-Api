package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UploadBeforeTrackingException : WhatnowCodeException(
    ImageErrorCode.UPLOAD_BEFORE_TRACKING,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UploadBeforeTrackingException()
    }
}
