package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import javax.persistence.DiscriminatorValue
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity
@DiscriminatorValue("INTERACTION_ATTAINMENT")
class InteractionAttainmentNotification(
    var promiseId: Long,

    var senderUserId: Long,

    @Enumerated(EnumType.STRING)
    var interactionType: InteractionType,

    override var targetUserId: Long,
) : Notification(targetUserId)
