package com.depromeet.whatnow.consts

const val AUTH_HEADER = "Authorization"
const val BEARER = "Bearer "
const val TOKEN_ROLE = "role"
const val TOKEN_TYPE = "type"
const val TOKEN_ISSUER = "WhatNow"
const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val REFRESH_TOKEN = "REFRESH_TOKEN"
const val KR_YES = "예"
const val KR_NO = "아니요"
const val MILLI_TO_SECOND = 1000
const val SERVICE_UNAVAILABLE = 503
const val BAD_REQUEST = 400
const val UNAUTHORIZED = 401
const val FORBIDDEN = 403
const val NOT_FOUND = 404
const val METHOD_NOT_ALLOWED = 405
const val INTERNAL_SERVER = 500
const val REQUEST_TIMEOUT = 408
const val TOO_MANY_REQUESTS = 429
const val ZERO = 0L
const val KID = "kid"
const val PROD = "prod"
const val DEV = "dev"
const val LOCAL = "local"
const val WITHDRAW_PREFIX = "withdraw"

val SWAGGER_PATTERNS = arrayOf(
    "/swagger-resources/**",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/v3/api-docs",
)
class WhatNowStatic
