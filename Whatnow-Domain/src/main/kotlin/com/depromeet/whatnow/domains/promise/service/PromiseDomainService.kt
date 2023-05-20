package com.depromeet.whatnow.domains.promise.service

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promise.exception.DoublePromiseException
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgressType
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class PromiseDomainService(
    val promiseAdaptor: PromiseAdaptor,
    val promiseValidator: PromiseValidator,
) {
    fun registerPromise(
        endTime: LocalDateTime,
        title: String,
        mainUserId: Long,
        meetPlace: PlaceVo?,
        promiseType: PromiseType,
        ): Promise {
        val promise = Promise(
            endTime = endTime,
            title = title,
            mainUserId = mainUserId,
            meetPlace = meetPlace,
            promiseType = promiseType
        )
        // 추 후 validation 추가
        promiseValidator.validateDoublePromise(promise)
        return promiseAdaptor.save(promise)
    }

    @Transactional
    fun updatePromiseTitle(promiseId: Long, title: String): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        // dirty checking
        promise.updateTitle(title)
        return promise
    }

    // 약속 지연시키기
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
    fun pendingPromiseProgressType(promiseId: Long, promiseProgressType: PromiseProgressType): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.pendingPromise()
        return promise
    }

    @Transactional
    fun endPromiseProgressType(promiseId: Long, promiseProgressType: PromiseProgressType): Promise {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.endPromise()
        return promise
    }

    // 약속 취소 처리
    @Transactional
    fun deletePromise(promiseId: Long) {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.deletePromise()
    }

    @Transactional
    fun findById(promiseId: Long): Promise {
        return promiseAdaptor.queryPromise(promiseId)
    }
}