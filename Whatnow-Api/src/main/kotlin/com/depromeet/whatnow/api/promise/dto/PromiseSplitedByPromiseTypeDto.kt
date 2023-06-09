package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.domains.promise.domain.Promise

data class PromiseSplitedByPromiseTypeDto(
    val promiseType: String,
    val promiseList: List<PromiseFindDto> = listOf()
){
    fun addPromise(promise: Promise) {
        promiseList.plus(PromiseFindDto.from(promise))
    }

}