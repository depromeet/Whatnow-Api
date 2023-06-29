package com.depromeet.whatnow.exception.custom

import com.depromeet.whatnow.exception.GlobalErrorCode
import com.depromeet.whatnow.exception.WhatnowCodeException

class UserIdParameterNotFoundException: WhatnowCodeException(
    GlobalErrorCode.USER_ID_PARAMETER_NOT_FOUND,
) {
    companion object {
        val EXCEPTION: WhatnowCodeException = UserIdParameterNotFoundException()
    }
}