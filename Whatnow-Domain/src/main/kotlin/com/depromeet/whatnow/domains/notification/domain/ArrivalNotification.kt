package com.depromeet.whatnow.domains.notification.domain

import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("ARRIVAL")
class ArrivalNotification(
    var promiseId: Long,

    var senderUserId: Long,

    override var targetUserId: Long,
) : Notification(targetUserId)
