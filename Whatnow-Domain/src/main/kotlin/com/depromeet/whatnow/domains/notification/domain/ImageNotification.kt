package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
@DiscriminatorValue("IMAGE")
class ImageNotification(
    @Enumerated(EnumType.STRING)
    var senderUserPromiseUserType: PromiseUserType,

    var senderUserId: Long,

    var promiseId: Long,

    var imageKey: String,

    override var targetUserId: Long,
) : Notification(targetUserId)
