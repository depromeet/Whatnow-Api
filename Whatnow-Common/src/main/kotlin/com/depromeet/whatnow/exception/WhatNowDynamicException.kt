package com.depromeet.whatnow.exception

class WhatNowDynamicException(
    val status: Int,
    val code: String,
    val reason: String?,
) : RuntimeException()
