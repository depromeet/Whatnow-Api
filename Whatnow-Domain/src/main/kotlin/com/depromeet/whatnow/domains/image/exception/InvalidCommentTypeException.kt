package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class InvalidCommentTypeException : WhatnowCodeException(
    ImageErrorCode.INVALID_COMMENT_TYPE,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = InvalidCommentTypeException()
    }
}
