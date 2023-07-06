package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.image.dto.PromiseImageDetailResponse
import com.depromeet.whatnow.api.image.dto.PromiseImageDto
import com.depromeet.whatnow.api.image.dto.PromiseImageResponse
import com.depromeet.whatnow.config.security.SecurityUtils
import com.depromeet.whatnow.domains.image.service.ImageDomainService
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService

@UseCase
class PromiseImageReadUseCase(
    val imageDomainService: ImageDomainService,
    val promiseUserDomainService: PromiseUserDomainService,
) {

    fun getLateAndWaitImagesByPromiseId(promiseId: Long): PromiseImageResponse {
        var userId = SecurityUtils.currentUserId

        // 사진을 조회하는 유저의 Late, Wait 상태를 조회하기 위해
        val promiseUser = promiseUserDomainService.findByPromiseIdAndUserId(promiseId, userId)

        val result: MutableMap<PromiseUserType, List<PromiseImageDto>> = mutableMapOf()
        val promiseImages = imageDomainService.getImagesByPromiseId(promiseId)

        // LATE의 사진
        result[PromiseUserType.LATE] = (
            promiseImages.filter { promiseImage -> promiseUserDomainService.findByPromiseIdAndUserId(promiseId, promiseImage.userId).promiseUserType == PromiseUserType.LATE }
                .sortedByDescending { promiseImage -> promiseImage.createdAt }
                .map { promiseImage ->
                    PromiseImageDto(promiseImage.imageKey, promiseImage.uri, promiseImage.userId)
                }.toList()
            )

        // WAIT의 사진 (가장 최신 1개)
        result[PromiseUserType.WAIT] = (
            promiseImages.filter { promiseImage -> promiseUserDomainService.findByPromiseIdAndUserId(promiseId, promiseImage.userId).promiseUserType == PromiseUserType.WAIT }
                .sortedByDescending { promiseImage -> promiseImage.createdAt }
            ).firstOrNull()?.let { promiseImage ->
            listOf(
                PromiseImageDto(promiseImage.imageKey, promiseImage.uri, promiseImage.userId),
            )
        } ?: listOf()

        return PromiseImageResponse(promiseUser.promiseUserType!!, result)
    }

    fun getImagesByPromiseId(promiseId: Long): List<PromiseImageDto> {
        return imageDomainService.getImagesByPromiseId(promiseId)
            .map { PromiseImageDto.from(it) }
    }

    fun getImageByImageKey(imageKey: String): PromiseImageDetailResponse {
        return imageDomainService.getImageByImageKey(imageKey)
            .let { PromiseImageDetailResponse.from(it) }
    }
}
