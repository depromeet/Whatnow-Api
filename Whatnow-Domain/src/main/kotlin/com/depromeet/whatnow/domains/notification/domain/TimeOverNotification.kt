package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("TIMEOVER")
class TimeOverNotification(
    var promiseId: Long,

    var promiseUserType: PromiseUserType,

    override var targetUserId: Long,
) : Notification(targetUserId)
