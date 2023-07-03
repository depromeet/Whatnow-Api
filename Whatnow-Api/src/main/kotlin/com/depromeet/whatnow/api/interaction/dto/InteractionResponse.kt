package com.depromeet.whatnow.api.interaction.dto

import com.depromeet.whatnow.api.promiseprogress.dto.response.UserProgressResponse

data class InteractionResponse(
    val userProgressResponse: UserProgressResponse,
    val interactionDtoList: List<InteractionDto>,
)
