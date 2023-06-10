package com.depromeet.whatnow.domains.picture.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class LateUserInvalidCommentException: WhatnowCodeException(
    PictureErrorCode.LATE_USER_INVALID_COMMENT,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = LateUserInvalidCommentException()
    }
}
