package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UserImageNotFoundException : WhatnowCodeException(
    ImageErrorCode.USER_IMAGE_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UserImageNotFoundException()
    }
}
