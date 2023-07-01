package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseUserRecordUseCase(
    val promiseUserDomainService: PromiseUserDomainService,
) {
    fun createPromiseUser(promiseId: Long, userId: Long, userLocation: CoordinateVo) {
        promiseUserDomainService.createPromiseUser(
            PromiseUser(
                promiseId = promiseId,
                userId = userId,
                userLocation = userLocation,
            ),
        )
    }

    fun cancelPromise(promiseId: Long, userId: Long) {
        promiseUserDomainService.withDraw(promiseId, userId)
    }

    fun updatePromiseUserType(promiseId: Long, userId: Long, promiseUserType: PromiseUserType) {
        promiseUserDomainService.updatePromiseUserType(promiseId, userId, promiseUserType)
    }
}
