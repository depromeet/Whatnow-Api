package com.depromeet.whatnow.domains.promiseprogress.adapter

import com.depromeet.whatnow.exception.WhatnowCodeException

class PromiseProgressNotFoundException : WhatnowCodeException(
    PromiseProgressCode.NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = PromiseProgressNotFoundException()
    }
}
