package com.depromeet.whatnow.domains.picture.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_picture")
class Picture(
    var userId: Long,

    var promiseId: Long,

    var uri: String,

    var uuid: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    val id: Long? = null,
) : BaseTimeEntity()
