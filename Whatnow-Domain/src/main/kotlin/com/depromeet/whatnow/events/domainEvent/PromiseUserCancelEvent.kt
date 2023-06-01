package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseUserCancelEvent(
    promiseUserId: Long,
) : DomainEvent()
