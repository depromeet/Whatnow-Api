package com.depromeet.whatnow.api.promiseprogress.dto.response

import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgressGroup

data class PromiseProgressDto(
    val group: PromiseProgressGroup,
    val code: String,
    val kr: String,
    val img: String,
) {
    companion object {
        fun from(p: PromiseProgress): PromiseProgressDto {
            return PromiseProgressDto(p.group, p.code, p.kr, p.img)
        }
    }
}
