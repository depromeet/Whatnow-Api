package com.depromeet.whatnow.domains.promise.service

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgressGroup
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
        // dirty checking
        promise.updateTitle(title)
        return promise
    }

    @Transactional
    fun delayPromise(promiseId: Long, endTime: LocalDateTime): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.delayPromise(endTime)
        return promise
    }

    // 약속장소 변경
    @Transactional
    fun movePromisePlace(promiseId: Long, placeVo: PlaceVo): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.movePlace(placeVo)
        return promise
    }

    // 약속 진행상태(출발 전, 이동 중, 도착, ETC) 변경
    @Transactional
    fun pendingPromiseProgressType(promiseId: Long, promiseProgressGroup: PromiseProgressGroup): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.pendingPromise()
        return promise
    }

    @Transactional
    fun endPromiseProgressType(promiseId: Long, promiseProgressGroup: PromiseProgressGroup): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.endPromise()
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
