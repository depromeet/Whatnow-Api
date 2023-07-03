package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress.DEFAULT
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseUserRecordUseCase(
    val promiseUserDomainService: PromiseUserDomainService,
) {
    fun createPromiseUser(promiseId: Long, userId: Long, userLocation: CoordinateVo): PromiseUserDto {
        return PromiseUserDto.of(
            promiseUserDomainService.createPromiseUser(
                PromiseUser(
                    promiseId = promiseId,
                    userId = userId,
                    userLocation = userLocation,
                ),
            ),
            progress = DEFAULT,
        )
    }

    fun cancelPromise(promiseId: Long, userId: Long) {
        promiseUserDomainService.withDraw(promiseId, userId)
    }

    fun updatePromiseUserType(promiseId: Long, userId: Long, promiseUserType: PromiseUserType): PromiseUserDto {
        return PromiseUserDto.of(
            promiseUserDomainService.updatePromiseUserType(
                promiseId = promiseId,
                userId = userId,
                promiseUserType = promiseUserType,
            ),
            progress = DEFAULT,
        )
    }
}
