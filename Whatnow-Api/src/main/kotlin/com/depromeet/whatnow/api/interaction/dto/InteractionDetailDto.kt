package com.depromeet.whatnow.api.interaction.dto

import com.depromeet.whatnow.common.vo.UserInfoVo
import com.depromeet.whatnow.domains.interaction.domain.InteractionType

data class InteractionDetailDto(
    val senderUser: UserInfoVo,
    val count: Long,
    val interactionType: InteractionType,
)
