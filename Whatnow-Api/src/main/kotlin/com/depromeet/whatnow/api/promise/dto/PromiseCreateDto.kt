package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseCreateDto(
    val title: String,
    val mainUserId: Long,
    val meetPlace: PlaceVo?,
    val endTime: LocalDateTime,
    val inviteCode: String,
) {
    companion object {
        fun of(p: Promise?, inviteCode: String): PromiseCreateDto {
            // Check if p is null and handle the null case appropriately
            if (p == null) {
                // Return a default or placeholder value for PromiseDto
                return PromiseCreateDto("", 1L, null, LocalDateTime.now(), "")
            }

            // Process non-null Promise object
            return PromiseCreateDto(p.title, p.mainUserId, p.meetPlace, p.endTime, inviteCode)
        }
    }
}
