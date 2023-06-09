package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.dto.PromiseSplitedByPromiseTypeDto
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType.BEFORE
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import java.time.LocalDateTime

@UseCase
class PromiseRegisterUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
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

    fun updatePromiseEndTime(promiseId: Long, endTime: LocalDateTime): PromiseDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        promise.updateEndTime(endTime)
        return PromiseDto.from(promise)
    }

    fun findPromiseByUserIdSeperatedType(userId: Long): List<PromiseSplitedByPromiseTypeDto> {
        // 참여한 유저를 기준으로 약속 조회 (방장 기준이 아님)
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        val userIds = promiseUsers.map { it.userId }
        val promiseIds = promiseUsers.map { it.promiseId }

//        예정된 약속
        val expectedPromises = PromiseSplitedByPromiseTypeDto(promiseType = "BEFORE")
//        지난 약속
        val pastPromises = PromiseSplitedByPromiseTypeDto(promiseType = "PAST")

        for (promise in promiseUsers) {
            val promise = promiseAdaptor.queryPromise(promise.id!!)
            var promiseType = promise.promiseType
            if (promiseType == BEFORE) {
                expectedPromises.addPromise(promise)
            } else {
                pastPromises.addPromise(promise)
            }
        }
        val listOf1 = listOf<PromiseSplitedByPromiseTypeDto>()
        listOf1.plus(expectedPromises)
        listOf1.plus(pastPromises)

        return listOf1

    }
}
