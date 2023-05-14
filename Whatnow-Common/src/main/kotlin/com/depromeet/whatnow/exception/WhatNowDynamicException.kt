package com.depromeet.whatnow.exception

class WhatNowDynamicException : RuntimeException() {
    val status = 0
    val code: String? = null
    val reason: String? = null
}
