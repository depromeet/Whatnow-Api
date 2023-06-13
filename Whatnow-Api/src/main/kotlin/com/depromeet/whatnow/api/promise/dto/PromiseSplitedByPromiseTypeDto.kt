package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType

data class PromiseSplitedByPromiseTypeDto(
    val promiseType: PromiseType,
    val promiseList: List<PromiseFindDto> = listOf(),
) {
    fun addPromise(promise: Promise, users: List<UserInfoVo>) {
        promiseList.plus(PromiseFindDto.from(promise, users))
    }
}
