package com.depromeet.whatnow.domains.image.exception

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseImageNotFoundException : WhatnowCodeException(
    ImageErrorCode.PROMISE_IMAGE_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseImageNotFoundException()
    }
}
