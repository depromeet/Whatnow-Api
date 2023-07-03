package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseUserReadUseCase(
    val promiseUserDomainService: PromiseUserDomainService,
    val progressHistoryAdapter: ProgressHistoryAdapter,
    val promiseUserAdaptor: PromiseUserAdaptor,
) {
    fun findByPromiseId(promiseId: Long): List<PromiseUserDto> {
        val progressHistories = progressHistoryAdapter.findByPromiseId(promiseId)
        val promiseUserDtos = mutableListOf<PromiseUserDto>()
        val findByPromiseId = promiseUserDomainService.findByPromiseId(promiseId)
        findByPromiseId.forEach { promiseUser ->
            val progressHistory = progressHistories.find { it.userId == promiseUser.userId }
            promiseUserDtos.add(PromiseUserDto.of(promiseUser, progressHistory!!.currentPromiseProgress))
        }
        return promiseUserDtos
    }

    fun findPromiseUserByPromiseIdAndUserId(promiseId: Long, userId: Long): PromiseUserDto {
        val promiseUsers = promiseUserAdaptor.findByPromiseIdAndUserId(promiseId, userId)
        val progressHistory = progressHistoryAdapter.findByPromiseIdAndUserId(promiseId, userId)
        return PromiseUserDto.of(promiseUsers, progressHistory.currentPromiseProgress)
    }
}
