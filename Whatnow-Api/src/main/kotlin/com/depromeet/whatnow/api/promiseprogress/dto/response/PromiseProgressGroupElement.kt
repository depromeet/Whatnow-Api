package com.depromeet.whatnow.api.promiseprogress.dto.response

import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgressGroup

data class PromiseProgressGroupElement(
    val group: PromiseProgressGroup,
    val progresses: List<PromiseProgressDto>,
)
