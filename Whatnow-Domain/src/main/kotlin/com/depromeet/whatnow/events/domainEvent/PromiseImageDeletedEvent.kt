package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.config.s3.ImageFileExtension

class PromiseImageDeletedEvent(
    val promiseId: Long,
    val imageKey: String,
    val imageFileExtension: ImageFileExtension,
) : DomainEvent()
