package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseSplitedByPromiseTypeDto
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.user.repository.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@UseCase
class PromiseReadUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userRepository: UserRepository,
) {
    /**
     * method desc: 유저가 참여한 약속들을 약속 종류(BEFORE, PAST)에 따라 분리해서 조회
     * @param userId: 유저 아이디
     * @return List<PromiseSplitByPromiseTypeDto>: 약속 종류에 따라 분리된 약속들
     */
    fun findPromiseByUserIdSeparatedType(): List<PromiseSplitedByPromiseTypeDto> {
        val userId: Long = SecurityUtils.currentUserId

        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        val promiseSplitByPromiseTypeDto = mutableListOf<PromiseSplitedByPromiseTypeDto>()

        for (promiseUser in promiseUsers) {
            val promise = promiseAdaptor.queryPromise(promiseUser.promiseId)
            val eachPromiseUsers = promiseUserAdaptor.findByPromiseId(promiseUser.promiseId)
            val participant = getParticipantUserInfo(eachPromiseUsers)
            val promiseType = if (promise.promiseType == PromiseType.BEFORE) "BEFORE" else "PAST"
            val promiseFindDto = PromiseFindDto.from(promise, participant)
            val dto = PromiseSplitedByPromiseTypeDto(promiseType, listOf(promiseFindDto))
            promiseSplitByPromiseTypeDto += dto
        }
        return promiseSplitByPromiseTypeDto
    }

    /**
     * method name: findPromiseByUserIdSeparatedWithYearMonth
     * description: 유저가 참여한 약속들 중 특정 년월에 해당하는 약속들을 조회한다
     */
    fun findPromiseByUserIdYearMonth(yearMonth: String): List<PromiseFindDto> {
        val userId: Long = SecurityUtils.currentUserId
        return findPromisesByUserId(userId)
            .filter { isSameYearMonth(it.endTime, yearMonth) }
            .map { promise ->
                // 약속에 참여한 유저들
                val participants = getParticipantUserInfo(promiseUserAdaptor.findByPromiseId(promise.id!!))
                PromiseFindDto(
                    title = promise.title,
                    address = promise.meetPlace!!.address,
                    endTime = promise.endTime,
                    users = participants,
                )
            }
    }

    fun findPromisesByUserId(userId: Long): List<Promise> {
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        return promiseUsers.map { promiseAdaptor.queryPromise(it.promiseId) }
    }

    private fun getParticipantUserInfo(promiseUsers: List<PromiseUser>): List<UserInfoVo> {
        return promiseUsers.mapNotNull { eachUser ->
            val user = userRepository.findById(eachUser.userId).orElse(null)
            user?.let { UserInfoVo.from(it) }
        }
    }

    private fun isSameYearMonth(dateTime: LocalDateTime, yearMonth: String): Boolean {
        val pattern = DateTimeFormatter.ofPattern("yyyy.MM")
        val formattedDateTime = dateTime.format(pattern)
        return formattedDateTime == yearMonth
    }
}
