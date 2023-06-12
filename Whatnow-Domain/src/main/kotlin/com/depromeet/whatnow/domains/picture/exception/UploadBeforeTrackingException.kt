package com.depromeet.whatnow.domains.picture.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UploadBeforeTrackingException : WhatnowCodeException(
    PictureErrorCode.UPLOAD_BEFORE_TRACKING,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UploadBeforeTrackingException()
    }
}
