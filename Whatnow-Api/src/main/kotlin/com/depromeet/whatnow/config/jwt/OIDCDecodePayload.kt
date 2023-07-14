package com.depromeet.whatnow.config.jwt

data class OIDCDecodePayload(
    /** issuer ex https://kauth.kakao.com */

    private val iss: String,

    /** client id  */
    val aud: String,

    /** oauth provider account unique id  */
    val sub: String,

)
