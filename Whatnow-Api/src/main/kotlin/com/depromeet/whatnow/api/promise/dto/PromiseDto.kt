package com.depromeet.whatnow.api.promise.dto

import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.domain.Promise
import java.time.LocalDateTime

data class PromiseDto(
    val title: String,
    val mainUserId: Long,
    val meetPlace: PlaceVo?,
    val endTime: LocalDateTime,
) {
    companion object {
        fun from(p: Promise?): PromiseDto {
            // Check if p is null and handle the null case appropriately
            if (p == null) {
                // Return a default or placeholder value for PromiseDto
                return PromiseDto("", 1L, null, LocalDateTime.now())
            }

            // Process non-null Promise object
            return PromiseDto(p.title, p.mainUserId, p.meetPlace, p.endTime)
        }
    }
}
