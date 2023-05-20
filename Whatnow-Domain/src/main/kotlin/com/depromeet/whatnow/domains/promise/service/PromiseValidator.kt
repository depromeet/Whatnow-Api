package com.depromeet.whatnow.domains.promise.service

import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.exception.DoublePromiseException
import org.springframework.stereotype.Component

@Component
class PromiseValidator(
    val promiseAdaptor: PromiseAdaptor,
) {
    /**
     * validation: @endTime 현재 날짜와 비교해서 일치하지 않아야함
     * */
    fun validateDoublePromise(promise: Promise) {
        val promises = promiseAdaptor.queryPromiseByMainUserIdAfterNow(promise.mainUserId)
        promises.filter { it.endTime > promise.endTime }.forEach {
            // 약속시간이 1시간 사이에 겹치면 등록 불가능
            if (it.endTime.dayOfYear == promise.endTime.dayOfYear) {
                throw DoublePromiseException.EXCEPTION
            }
        }
    }
}
