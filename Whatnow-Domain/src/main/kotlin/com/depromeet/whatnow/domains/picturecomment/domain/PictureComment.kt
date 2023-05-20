package com.depromeet.whatnow.domains.picturecomment.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_picture_comment")
class PictureComment(
    var pictureId: Long,

    var userId: Long,

    var comment: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_comment_id")
    val id: Long? = null,
) : BaseTimeEntity()
