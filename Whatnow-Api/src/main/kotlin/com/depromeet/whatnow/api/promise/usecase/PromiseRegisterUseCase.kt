package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.user.exception.ForbiddenUserException
import java.time.LocalDateTime
import javax.transaction.Transactional

@UseCase
class PromiseRegisterUseCase(
    val promiseAdaptor: PromiseAdaptor,
) {
    @Transactional
    fun createPromise(promiseRequest: PromiseRequest): PromiseDto {
        val promise = promiseAdaptor.save(
            Promise(
                title = promiseRequest.title,
                mainUserId = promiseRequest.mainUserId,
                meetPlace = promiseRequest.meetPlace,
                endTime = promiseRequest.endTime,
            ),
        )
        return PromiseDto.from(promise)
    }

    @Transactional
    fun updatePromiseMeetPlace(promiseId: Long, meetPlace: PlaceVo): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.updateMeetPlace(meetPlace)
        return PromiseDto.from(promise)
    }

    @Transactional
    fun updatePromiseEndTime(promiseId: Long, endTime: LocalDateTime): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.updateEndTime(endTime)
        return PromiseDto.from(promise)
    }

    @Transactional
    fun deletePromise(promiseId: Long, userId: Long) {
        val promise = promiseAdaptor.queryPromise(promiseId)
        // 방장만 전체 파토할 수 있다.
        if (userId == promise.mainUserId) {
            promise.deletePromise()
        } else {
            throw ForbiddenUserException()
        }
    }
}
