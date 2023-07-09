package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_notification")
class Notification(
    @Enumerated(EnumType.STRING)
    var notificationType: NotificationType,

    @Enumerated(EnumType.STRING)
    var interactionType: InteractionType?,

    var userId: Long,

    @ElementCollection
    var targetUserIds: Set<Long>,

    var targetId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long? = null,
) : BaseTimeEntity()
