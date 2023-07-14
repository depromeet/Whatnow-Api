package com.depromeet.whatnow.domains.promise.service

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PromiseDomainService(
    val promiseAdaptor: PromiseAdaptor,
) {
    @Transactional
    fun updatePromiseTitle(promiseId: Long, title: String): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.updateTitle(title)
        return promise
    }

    // 약속 취소 처리
    @Transactional
    fun deletePromise(promise: Promise) {
        promise.deletePromise()
    }

    fun findByPromiseId(promiseId: Long): Promise {
        return promiseAdaptor.queryPromise(promiseId)
    }

    @Transactional
    fun updateMeetPlace(promise: Promise, meetPlace: PlaceVo): Promise {
        promise.updateMeetPlace(meetPlace)
        return promise
    }

    @Transactional
    fun save(promise: Promise): Promise {
        return promiseAdaptor.save(promise)
    }

    @Transactional
    fun updateEndTime(promise: Promise, endTime: LocalDateTime): Promise {
        promise.updateEndTime(endTime)
        return promise
    }
}
