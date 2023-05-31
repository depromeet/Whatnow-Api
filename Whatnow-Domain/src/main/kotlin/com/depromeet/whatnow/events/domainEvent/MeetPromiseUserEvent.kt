package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

// 위치기반 완성되면 그때 event 발행 예정
class MeetPromiseUserEvent(
    val promiseId: Long,
    val userIds: List<Long>,
) : DomainEvent()
