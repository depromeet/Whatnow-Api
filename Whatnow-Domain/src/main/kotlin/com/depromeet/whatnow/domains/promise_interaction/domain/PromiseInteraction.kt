package com.depromeet.whatnow.domains.promise_interaction.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_promise_interaction")
class PromiseInteraction(
    var promiseId: Long,

    var interactionId: Long,

    var userId: Long,

    var count: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_interaction_id")
    val id: Long? = null
): BaseTimeEntity()