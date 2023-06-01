package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.READY
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType.valueOf
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseUserReadUseCase(
    val promiseUserDomainService: PromiseUserDomainService,
) {
    fun findByPromiseId(promiseId: Long): List<PromiseUserDto> {
        return promiseUserDomainService.findByPromiseId(promiseId).map { PromiseUserDto.from(it) }
    }
    fun findPromiseUserByPromiseUserType(promiseId: Long, userId: Long, status: String): List<PromiseUserDto> {
        return promiseUserDomainService.findByPromiseId(promiseId)
//            userType 와 status 를 비교해서 같은 것만 반환
            .filter {
                it.userId == userId && it.promiseUserType == valueOf(status)
            }
            .map { PromiseUserDto.from(it) }
    }

    fun findByUserIdOnReady(userId: Long): List<PromiseUserDto> {
        return promiseUserDomainService.findByUserId(userId)
            .filter { it.promiseUserType == READY }
            .map {
                PromiseUserDto.from(it)
            }
    }

    fun findByUserIdWithStatus(userId: Long, status: String): List<PromiseUserDto> {
        return promiseUserDomainService.findByUserId(userId)
            .filter { it.promiseUserType == valueOf(status) }
            .map {
                PromiseUserDto.from(it)
            }
    }
}
