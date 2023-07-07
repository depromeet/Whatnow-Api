package com.depromeet.whatnow.events.domainEvent

import com.depromeet.whatnow.common.aop.event.DomainEvent
import com.depromeet.whatnow.config.s3.ImageFileExtension

class UserProfileImageUpdatedEvent(
    val userId: Long,
    val imageKey: String,
    val fileExtension: ImageFileExtension,
) : DomainEvent()
