package com.depromeet.whatnow.api.image.dto

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import io.swagger.v3.oas.annotations.media.Schema

data class PromiseImageResponse(
    @Schema(description = "조회한 유저의 약속 상태 (LATE=빨강, WAIT=파랑)", example = "LATE")
    val promiseUserType: PromiseUserType,
    @Schema(description = "약속에 참여한 유저들의 사진 목록 (LATE, WAIT로 구분. WAIT는 가장 최신 1개만 존재")
    val promiseImages: MutableMap<PromiseUserType, List<PromiseImageDto>>,
)
