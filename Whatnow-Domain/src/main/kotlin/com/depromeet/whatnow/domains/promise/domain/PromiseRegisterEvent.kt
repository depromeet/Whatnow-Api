package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.aop.event.DomainEvent

class PromiseRegisterEvent(
    val promiseId: Long,
) : DomainEvent()

