package com.depromeet.whatnow.api.config.ncp

import com.depromeet.whatnow.api.config.kakao.KakaoKauthErrorCode
import com.depromeet.whatnow.api.config.kakao.KakaoKauthErrorCode.KOE_INVALID_REQUEST
import com.depromeet.whatnow.api.dto.KakaoKauthErrorResponse
import com.depromeet.whatnow.dto.ErrorReason
import com.depromeet.whatnow.exception.WhatNowDynamicException
import feign.Response
import feign.codec.ErrorDecoder

class NcpInfoErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String, response: Response): Exception {
        val body: KakaoKauthErrorResponse = KakaoKauthErrorResponse.from(response)
        try {
            val ncpErrorCode = KakaoKauthErrorCode.valueOf(body.errorCode)
            val errorReason: ErrorReason = ncpErrorCode.errorReason
            throw WhatNowDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason,
            )
        } catch (e: IllegalArgumentException) {
            val koeInvalidRequest = KOE_INVALID_REQUEST
            val errorReason: ErrorReason = koeInvalidRequest.errorReason
            throw WhatNowDynamicException(
                errorReason.status,
                errorReason.code,
                errorReason.reason,
            )
        }
    }
}
