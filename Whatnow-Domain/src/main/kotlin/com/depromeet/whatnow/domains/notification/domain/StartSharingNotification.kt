package com.depromeet.whatnow.domains.notification.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("START_SHARING")
class StartSharingNotification(
    var promiseId: Long,

    override var targetUserId: Long,
) : Notification(targetUserId)
