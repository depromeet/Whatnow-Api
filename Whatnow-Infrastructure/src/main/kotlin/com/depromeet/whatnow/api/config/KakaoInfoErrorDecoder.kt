package com.depromeet.whatnow.api.config

import com.depromeet.whatnow.exception.custom.OtherServerBadRequestException
import com.depromeet.whatnow.exception.custom.OtherServerExpiredTokenException
import com.depromeet.whatnow.exception.custom.OtherServerForbiddenException
import com.depromeet.whatnow.exception.custom.OtherServerUnauthorizedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder

class KakaoInfoErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        if (response.status() >= 400) {
            when (response.status()) {
                401 -> throw OtherServerUnauthorizedException.EXCEPTION
                403 -> throw OtherServerForbiddenException.EXCEPTION
                419 -> throw OtherServerExpiredTokenException.EXCEPTION
                else -> throw OtherServerBadRequestException.EXCEPTION
            }
        }
        return FeignException.errorStatus(methodKey, response)
    }
}
