package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PictureRegisterEvent(
    val userId: Long,
    val promiseId: Long,
) : DomainEvent()
