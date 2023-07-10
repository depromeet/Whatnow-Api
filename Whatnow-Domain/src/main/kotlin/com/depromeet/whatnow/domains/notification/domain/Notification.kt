package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
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

    @Enumerated(EnumType.STRING)
    var promiseUserType: PromiseUserType?,

    var userId: Long?,

    @ElementCollection
    var targetUserIds: Set<Long>,

    var targetId: Long?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun createForImage(userId: Long, targetUserId: Set<Long>, promiseImageId: Long): Notification {
            return Notification(
                notificationType = NotificationType.IMAGE,
                userId = userId,
                targetUserIds = targetUserId,
                targetId = promiseImageId,
                interactionType = null,
                promiseUserType = null,
            )
        }

        fun createForStartSharing(targetUserIds: Set<Long>, promiseId: Long): Notification {
            return Notification(
                notificationType = NotificationType.START_SHARING,
                userId = null,
                targetUserIds = targetUserIds,
                targetId = promiseId,
                interactionType = null,
                promiseUserType = null,
            )
        }

        fun createForTimeOver(targetUserIds: Set<Long>, promiseId: Long): Notification {
            return Notification(
                notificationType = NotificationType.TIMEOVER,
                userId = null,
                targetUserIds = targetUserIds,
                targetId = promiseId,
                interactionType = null,
                promiseUserType = null,
            )
        }

        fun createForInteraction(userId: Long, targetUserId: Long, interactionType: InteractionType): Notification {
            return Notification(
                notificationType = NotificationType.INTERACTION,
                userId = userId,
                targetUserIds = mutableSetOf(targetUserId),
                targetId = null,
                interactionType = interactionType,
                promiseUserType = null,
            )
        }
    }
}
