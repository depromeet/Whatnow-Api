package com.depromeet.whatnow.domains.interaction.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.consts.INTERACTION_FIXED_COUNT
import com.depromeet.whatnow.events.domainEvent.InteractionFixedEvent
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_interaction")
class Interaction(
    @Enumerated(EnumType.STRING)
    var interactionType: InteractionType,

    var userId: Long,

    var promiseId: Long,

    var count: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    fun increment() {
        count += 1
        if (count == INTERACTION_FIXED_COUNT) {
            Events.raise(InteractionFixedEvent(promiseId, userId, interactionType))
        }
    }

    companion object {
        fun of(promiseId: Long, userId: Long, interactionType: InteractionType): Interaction {
            return Interaction(interactionType, userId, promiseId, 0)
        }
    }
}
