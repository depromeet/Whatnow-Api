package com.depromeet.whatnow.domains.picture.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class InvalidCommentTypeException : WhatnowCodeException(
    PictureErrorCode.INVALID_COMMENT_TYPE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InvalidCommentTypeException()
    }
}
