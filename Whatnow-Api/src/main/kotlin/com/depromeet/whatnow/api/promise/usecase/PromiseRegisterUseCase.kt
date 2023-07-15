package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.service.PromiseDomainService
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@UseCase
class PromiseRegisterUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseDomainService: PromiseDomainService,
) {
    @Transactional
    fun createPromise(promiseRequest: PromiseRequest): PromiseDto {
        val promise = promiseDomainService.save(
            Promise(
                title = promiseRequest.title,
                mainUserId = promiseRequest.mainUserId,
                meetPlace = promiseRequest.meetPlace,
                endTime = promiseRequest.endTime,
            ),
        )
        promise.createPromiseEvent()
        return PromiseDto.from(promise)
    }

    fun updatePromiseMeetPlace(promiseId: Long, meetPlace: PlaceVo): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        val updatePromise = promiseDomainService.updateMeetPlace(promise, meetPlace)
        return PromiseDto.from(updatePromise)
    }

    fun updatePromiseTitle(promiseId: Long, title: String): PromiseDto {
        val updatePromise = promiseDomainService.updatePromiseTitle(promiseId, title)
        return PromiseDto.from(updatePromise)
    }

    fun updatePromiseEndTime(promiseId: Long, endTime: LocalDateTime): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        val updatePromise = promiseDomainService.updateEndTime(promise, endTime)
        return PromiseDto.from(updatePromise)
    }

    fun deletePromise(promiseId: Long) {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promiseDomainService.deletePromise(promise)
    }
}
