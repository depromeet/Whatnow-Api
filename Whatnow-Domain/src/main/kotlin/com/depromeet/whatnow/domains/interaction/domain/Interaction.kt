package com.depromeet.whatnow.domains.interaction.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_interaction")
class Interaction(
    @Enumerated(EnumType.STRING)
    var interactionType: InteractionType,

    var message: String,

    var img: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    val id: Long? = null
): BaseTimeEntity()
