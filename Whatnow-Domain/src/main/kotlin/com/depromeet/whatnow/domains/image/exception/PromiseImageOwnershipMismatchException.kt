package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseImageOwnershipMismatchException : WhatnowCodeException(
    ImageErrorCode.PROMISE_IMAGE_OWNERSHIP_MISMATCH,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseImageOwnershipMismatchException()
    }
}
