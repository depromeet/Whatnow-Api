package com.depromeet.whatnow.api.promise.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.interaction.dto.InteractionDto
import com.depromeet.whatnow.api.promise.dto.LocationCapture
import com.depromeet.whatnow.api.promise.dto.PromiseDetailDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseUserInfoVo
import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.image.adapter.PromiseImageAdapter
import com.depromeet.whatnow.domains.interaction.adapter.InteractionAdapter
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promise.domain.Promise
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.repository.UserRepository
import java.time.LocalDateTime
import java.time.YearMonth
import kotlin.collections.Map.Entry

@UseCase
class PromiseReadUseCase(
    val promiseAdaptor: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val userAdapter: UserAdapter,
    val userRepository: UserRepository,
    val interactionAdapter: InteractionAdapter,
    val promiseImageAdapter: PromiseImageAdapter,
) {
    /**
     * method desc: 유저가 참여한 약속들을 약속 종류(BEFORE, PAST)에 따라 분리해서 조회
     * @param userId: 유저 아이디
     * @return List<PromiseSplitByPromiseTypeDto>: 약속 종류에 따라 분리된 약속들
     */
    fun findPromiseByUserIdSeparatedType(): Map<PromiseType, MutableList<PromiseFindDto>> {
        val userId: Long = SecurityUtils.currentUserId
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        val promiseIds = promiseUsers.map { it.promiseId }
        val promises = promiseAdaptor.queryPromises(promiseIds).associateBy { it.id }
        val promiseUsersByPromiseId = promiseUserAdaptor.findByPromiseIds(promiseIds).groupBy { it.promiseId }
        val promiseSplitByPromiseTypeDto = mutableMapOf<PromiseType, MutableList<PromiseFindDto>>()

        promiseUsers.forEach() { promiseUser ->
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
                    return@forEach
                }
                null -> {
                    return@forEach
                }
                else -> {
                    return@forEach
                }
            }

            val action: (Entry<PromiseType, MutableList<PromiseFindDto>>) -> Unit = { (promiseType, promiseFindDtos) ->
                // promiseType == BEFORE 인 것만 정렬
                when (promiseType) {
                    // 예정된 약속은 오름차순 정렬
                    PromiseType.BEFORE -> promiseFindDtos.sortBy { it.endTime }
                    // 종료된 약속은 내림차순 정렬
                    else -> promiseFindDtos.sortByDescending { it.endTime }
                }
            }
            promiseSplitByPromiseTypeDto.forEach(action)
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
                PromiseFindDto.of(
                    promise = promise,
                    users = participants,
                )
            }.sortedByDescending { it.endTime }
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

                // 유저의 Interaction 정보를 조회 (인터렉션 개수 순으로 내림차순 정렬)
                val interactions =
                    interactionAdapter.queryAllInteraction(promiseUser.promiseId, promiseUser.userId)
                        .map { InteractionDto.from(it) }
                        .sortedByDescending { interactionDto -> interactionDto.count }
                user?.let {
                    PromiseUserInfoVo.of(it, promiseUser.promiseUserType, interactions)
                }
            }

            val promiseImagesUrls = promiseImageAdapter.findAllByPromiseId(promise.id!!)
                .sortedBy { it.createdAt }
                .map { it.uri }

            val timeOverLocations = promiseUsers.mapNotNull { promiseUser ->
                promiseUser.userLocation?.let { location ->
                    LocationCapture(userId = promiseUser.userId, coordinateVo = location)
                }
            }

            result.add(
                PromiseDetailDto.of(
                    promise = promise,
                    promiseUsers = promiseUserInfoVos,
                    timeOverLocations = timeOverLocations,
                    promiseImageUrls = promiseImagesUrls,
                ),
            )
        }

        return result.sortedByDescending { it.endTime }
    }

    fun findPromiseActive(promiseId: Long): Boolean {
        val promise = promiseAdaptor.queryPromise(promiseId)
        // 현재 시간이 endTime 의1시간 전과 30분 이후 사이에 있으면 true
        val now = LocalDateTime.now()
        val endTime = promise.endTime
        return now.isAfter(endTime.minusHours(1)) && now.isBefore(endTime.plusMinutes(30))
    }
    private fun getParticipantUserInfo(promiseUsers: List<PromiseUser>): List<UserInfoVo> {
        val userIds = promiseUsers.map { it.userId }
        val users = userRepository.findAllById(userIds)
        return users.mapNotNull { UserInfoVo.from(it) }
    }

    private fun findPromisesByUserId(userId: Long): List<Promise> {
        val promiseUsers = promiseUserAdaptor.findByUserId(userId)
        return promiseUsers.map { promiseAdaptor.queryPromise(it.promiseId) }.sortedByDescending { it.endTime }
    }

    fun findByPromiseId(promiseId: Long): PromiseFindDto {
        val promise = promiseAdaptor.queryPromise(promiseId)
        val promiseUsers = promiseUserAdaptor.findByPromiseId(promiseId)
        val participants = getParticipantUserInfo(promiseUsers)
        return PromiseFindDto.of(promise, participants)
    }
}
