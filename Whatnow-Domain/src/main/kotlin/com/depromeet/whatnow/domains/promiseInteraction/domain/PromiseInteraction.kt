package com.depromeet.whatnow.domains.promiseInteraction.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

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
    val id: Long? = null,
) : BaseTimeEntity()
