package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promise.dto.LocationCapture
import com.depromeet.whatnow.api.promise.dto.PromiseDetailDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseUserInfoVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.repository.UserRepository
import java.time.YearMonth

@UseCase
class PromiseReadUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val userRepository: UserRepository,
) {
    /**
     * method desc: 유저가 참여한 약속들을 약속 종류(BEFORE, PAST)에 따라 분리해서 조회
     * @param userId: 유저 아이디
     * @return List<PromiseSplitByPromiseTypeDto>: 약속 종류에 따라 분리된 약속들
     */
    fun findPromiseByUserIdSeparatedType(): Map<PromiseType, MutableList<PromiseFindDto>> {
        val userId: Long = SecurityUtils.currentUserId

        // 내가 참여한 약속들(약속유저)
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        val promiseIds = promiseUsers.map { it.promiseId }
        //  내가 참여한 약속들
        val promises = promiseAdaptor.queryPromises(promiseIds).associateBy { it.id }
        // 내가 참여한 약속들에 참여한 친구들
        val promiseUsersByPromiseId = promiseUserAdaptor.findByPromiseIds(promiseIds).groupBy { it.promiseId }
        val promiseSplitByPromiseTypeDto = mutableMapOf<PromiseType, MutableList<PromiseFindDto>>()

        for (promiseUser in promiseUsers) {
            // 약속 하나씩
            val promise = promises[promiseUser.promiseId]
            val eachPromiseUsers = promiseUsersByPromiseId[promiseUser.promiseId] ?: emptyList()
            val participant = getParticipantUserInfo(eachPromiseUsers)

            when (promise?.promiseType) {
                PromiseType.BEFORE, PromiseType.END -> {
                    val promiseType = promise.promiseType
                    val promiseFindDto = PromiseFindDto.of(promise, participant)

                    promiseSplitByPromiseTypeDto.getOrPut(promiseType) { mutableListOf() }
                        .add(promiseFindDto)
                }

                PromiseType.DELETED -> {
                    // Do nothing for deleted promises.
                }
            }
        }
        return promiseSplitByPromiseTypeDto
    }

    /**
     * method name: findPromiseByUserIdSeparatedWithYearMonth
     * description: 유저가 참여한 약속들 중 특정 년월에 해당하는 약속들을 조회한다
     */
    fun findPromiseByUserIdYearMonth(yearMonth: YearMonth): List<PromiseFindDto> {
        val userId: Long = SecurityUtils.currentUserId
        return findPromisesByUserId(userId)
            .filter { it.endTime.year == yearMonth.year && it.endTime.month == yearMonth.month }
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

    fun getParticipantUserInfo(promiseUsers: List<PromiseUser>): List<UserInfoVo> {
        val userIds = promiseUsers.map { it.userId }
        val users = userRepository.findAllById(userIds)
        return users.mapNotNull { UserInfoVo.from(it) }
    }

    fun findPromiseWithStatus(promiseType: PromiseType): List<PromiseFindDto> {
        val userId: Long = SecurityUtils.currentUserId
        val promises = promiseAdaptor.queryPromisesWithStatus(userId, promiseType)

        val promiseUsersByPromiseId = promiseUserAdaptor.findByUniquePromiseIds(promises.map { it.id!! })
            .groupBy { it.promiseId }

        val promiseFindDtos = mutableListOf<PromiseFindDto>()

//        약속한
        for (promise in promises) {
            val participants = promiseUsersByPromiseId[promise.id]?.let { getParticipantUserInfo(it) }
            promiseFindDtos.add(PromiseFindDto.of(promise, participants!!))
        }

        return promiseFindDtos
    }

    fun findPromiseDetailByStatus(promiseType: PromiseType): List<PromiseDetailDto> {
        val userId: Long = SecurityUtils.currentUserId
        val promiseUsersByPromiseId = promiseUserAdaptor.findByUserId(userId)
        val promiseIds = promiseUsersByPromiseId.map { it.promiseId }
        val promises = promiseAdaptor.queryPromises(promiseIds)
        val uniqueUsers = promiseUsersByPromiseId.distinctBy { it.userId }
        val users = userAdapter.queryUsers(uniqueUsers.map { it.userId })
        val result = mutableListOf<PromiseDetailDto>()

        for (promise in promises) {
            val promiseUsers = promiseUsersByPromiseId.filter { it.promiseId == promise.id }
            val promiseUserInfoVos = promiseUsers.mapNotNull { promiseUser ->
                val user = users.find { it.id == promiseUser.userId }
                user?.let { PromiseUserInfoVo.of(it, promiseUser.promiseUserType!!) }
            }

            val timeOverLocations = promiseUsers.mapNotNull { promiseUser ->
                promiseUser.userLocation?.let { location ->
                    LocationCapture(userId = promiseUser.userId, coordinateVo = location)
                }
            }

            result.add(
                PromiseDetailDto(
                    title = promise.title,
                    date = promise.endTime,
                    promiseUsers = promiseUserInfoVos,
                    promiseImageUrls = null,
                    timeOverLocations = timeOverLocations,
                ),
            )
        }
        return result
    }
}
