package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.common.vo.CoordinateVo

// 만났다 이벤트
class PromiseUserMeetEvent(
    val promiseId: Long,
    val coordinateVo: CoordinateVo,
) : DomainEvent()