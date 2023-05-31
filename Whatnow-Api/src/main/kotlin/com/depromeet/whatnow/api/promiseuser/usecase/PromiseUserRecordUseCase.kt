package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseUserRecordUseCase(
    val promiseUserDomainService: PromiseUserDomainService,
) {
    fun createPromiseUser(promiseId: Long, userId: Long, userLocation: CoordinateVo): PromiseUserDto {
        val createPromiseUser = promiseUserDomainService.createPromiseUser(
            PromiseUser(
                promiseId = promiseId,
                userId = userId,
                userLocation = userLocation,
            ),
        )
        return PromiseUserDto.from(createPromiseUser)
    }

    fun cancelPromise(promiseId: Long, userId: Long): PromiseUserDto {
        promiseUserDomainService.withDraw(promiseId, userId)
        return PromiseUserDto.from(promiseUserDomainService.queryPromiseUser(promiseId))
    }

    fun updatePromiseUserType(promiseId: Long, userId: Long, status: String): PromiseUserDto {
        val promiseUser = promiseUserDomainService.updatePromiseUserType(promiseId, userId, status)
        return PromiseUserDto.from(promiseUser!!)
    }
}
