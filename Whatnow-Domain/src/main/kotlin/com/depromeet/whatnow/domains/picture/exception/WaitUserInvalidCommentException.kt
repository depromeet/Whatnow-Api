package com.depromeet.whatnow.domains.picture.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class WaitUserInvalidCommentException: WhatnowCodeException(
    PictureErrorCode.WAIT_USER_INVALID_COMMENT,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = WaitUserInvalidCommentException()
    }
}
