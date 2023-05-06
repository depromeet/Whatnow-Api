package com.depromeet.promiseapi.global.common.exception

data class BasicResponse(
    var code: Int,
    var message: String,
    var date: Any? = null
) {

}