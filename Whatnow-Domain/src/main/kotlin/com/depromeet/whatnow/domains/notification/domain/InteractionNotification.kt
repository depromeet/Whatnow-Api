package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity

@Entity
@DiscriminatorValue("INTERACTION")
class InteractionNotification(
    var promiseId: Long,

    var senderUserId: Long,

    var interactionType: InteractionType,

    override var targetUserId: Long,
) : Notification(targetUserId)
