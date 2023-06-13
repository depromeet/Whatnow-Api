package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.common.vo.PlaceVo

data class PromiseUpdateMeetPlaceEvent(
    val id: Long,
    val meetPlace: PlaceVo,
) : DomainEvent()
