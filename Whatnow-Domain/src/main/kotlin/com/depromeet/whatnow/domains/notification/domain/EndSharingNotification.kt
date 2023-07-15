package com.depromeet.whatnow.domains.notification.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("END_SHARING")
class EndSharingNotification(
    var promiseId: Long,

    override var targetUserId: Long,
) : Notification(targetUserId)
