package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class UserImageOwnershipMismatchException : WhatnowCodeException(
    ImageErrorCode.USER_IMAGE_OWNERSHIP_MISMATCH,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UserImageOwnershipMismatchException()
    }
}
