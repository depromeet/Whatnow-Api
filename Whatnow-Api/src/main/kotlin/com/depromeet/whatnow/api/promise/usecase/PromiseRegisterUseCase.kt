package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise

@UseCase
class PromiseRegisterUseCase(
    val promiseAdaptor: PromiseAdaptor,
) {
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

    fun updatePromiseMeetPlace(promiseId: Long, meetPlace: PlaceVo): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.updateMeetPlace(meetPlace)
        return PromiseDto.from(promise)
    }
}
