package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Table(name = "tbl_notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
abstract class Notification(
    open val targetUserId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    open val id: Long? = null,
) : BaseTimeEntity() {

//    companion object {
//        fun createForImage(userId: Long, targetUserId: Set<Long>, promiseImageId: Set<Long>): Notification {
//            return Notification(
//                notificationType = NotificationType.IMAGE,
//                userId = userId,
//                targetUserIds = targetUserId,
//                targetId = promiseImageId,
//                interactionType = null,
//                promiseUserType = null,
//            )
//        }
//
//        fun createForStartSharing(targetUserIds: Set<Long>, promiseId: Long): Notification {
//            return Notification(
//                notificationType = NotificationType.START_SHARING,
//                userId = null,
//                targetUserIds = targetUserIds,
//                targetId = promiseId,
//                interactionType = null,
//                promiseUserType = null,
//            )
//        }
//
//        fun createForEndSharing(targetUserIds: Set<Long>, promiseId: Long): Notification {
//            return Notification(
//                notificationType = NotificationType.END_SHARING,
//                userId = null,
//                targetUserIds = targetUserIds,
//                targetId = promiseId,
//                interactionType = null,
//                promiseUserType = null,
//            )
//        }
//
//        fun createForTimeOver(targetUserIds: Set<Long>, promiseId: Long): Notification {
//            return Notification(
//                notificationType = NotificationType.TIMEOVER,
//                userId = null,
//                targetUserIds = targetUserIds,
//                targetId = promiseId,
//                interactionType = null,
//                promiseUserType = null,
//            )
//        }
//
//        fun createForInteraction(userId: Long, targetUserId: Long, interactionType: InteractionType): Notification {
//            return Notification(
//                notificationType = NotificationType.INTERACTION,
//                userId = userId,
//                targetUserIds = mutableSetOf(targetUserId),
//                targetId = null,
//                interactionType = interactionType,
//                promiseUserType = null,
//            )
//        }
//
//        fun createForInteractionAttainment(
//            userId: Long,
//            senderUserIds: Set<Long>,
//            interactionType: InteractionType,
//        ): Notification {
//            return Notification(
//                notificationType = NotificationType.INTERACTION_ATTAINMENT,
//                userId = userId,
//                targetUserIds = senderUserIds,
//                targetId = null,
//                interactionType = interactionType,
//                promiseUserType = null,
//            )
//        }
//    }
}
