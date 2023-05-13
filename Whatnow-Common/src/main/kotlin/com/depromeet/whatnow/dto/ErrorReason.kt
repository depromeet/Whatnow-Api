package com.depromeet.whatnow.dto

data class ErrorReason(
    val status: Int?,
    val code: String?,
    val reason: String?,
) {
    data class Builder(
        var status: Int? = null,
        var code: String? = null,
        var reason: String? = null,
    ) {
        fun status(status: Int?) = apply { this.status = status }
        fun code(code: String?) = apply { this.code = code }
        fun reason(reason: String?) = apply { this.reason = reason }
        fun build() = ErrorReason(status, code, reason)
    }
    companion object {
        fun builder() = Builder()
    }
}
