package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.dto.PromiseSplitedByPromiseTypeDto
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.user.repository.UserRepository
import java.time.LocalDateTime

@UseCase
class PromiseRegisterUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userRepository: UserRepository,
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

    fun findPromiseByUserIdSeparatedType(userId: Long): List<PromiseSplitedByPromiseTypeDto> {
        // 참여한 유저를 기준으로 약속 조회 (방장 기준이 아님)
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)

        val promiseSplitByPromiseTypeDto = mutableListOf<PromiseSplitedByPromiseTypeDto>()

        for (promiseUser in promiseUsers) {
            val promise = promiseAdaptor.queryPromise(promiseUser.promiseId)
            // 내가 참여한 약속들
            val eachPromiseUsers = promiseUserAdaptor.findByPromiseId(promiseUser.promiseId)
            // 내가 참여한 약속들에 참여한 사람들 정보
            val participant = getParticipantUserInfo(eachPromiseUsers)
            val promiseType = if (promise.promiseType == PromiseType.BEFORE) "BEFORE" else "PAST"
            val promiseFindDto = PromiseFindDto.from(promise, participant)
            val dto = PromiseSplitedByPromiseTypeDto(promiseType, listOf(promiseFindDto))
            promiseSplitByPromiseTypeDto += dto
        }
        return promiseSplitByPromiseTypeDto
    }

    private fun getParticipantUserInfo(promiseUsers: List<PromiseUser>): List<UserInfoVo> {
        return promiseUsers.map { eachUser ->
            val user = userRepository.findById(eachUser.userId).orElse(null)
            UserInfoVo.from(user)
        }
    }
}
