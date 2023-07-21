package com.depromeet.whatnow.domains.interactionhistory.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.domains.interaction.domain.InteractionType
import com.depromeet.whatnow.events.domainEvent.InteractionHistoryRegisterEvent
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.Table

@Entity
@Table(name = "tbl_interaction_history")
class InteractionHistory(
    var promiseId: Long,

    @Enumerated(EnumType.STRING)
    var interactionType: InteractionType,

    var userId: Long,

    var targetUserId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_history_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    @PostPersist
    fun createInteractionHistoryEvent() {
        Events.raise(InteractionHistoryRegisterEvent(this.promiseId, this.interactionType, this.userId, this.targetUserId))
    }

    companion object {
        fun of(
            promiseId: Long,
            interactionType: InteractionType,
            userId: Long,
            targetUserId: Long,
        ): InteractionHistory {
            return InteractionHistory(promiseId, interactionType, userId, targetUserId)
        }
    }
}
