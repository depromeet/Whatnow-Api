package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.common.vo.CoordinateVo

class PromiseUserUpdateLocationEvent(
    val promiseId: Long,
    val coordinateVo: CoordinateVo,
) : DomainEvent()
