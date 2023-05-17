package com.depromeet.whatnow.domains.pictureComment.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_picture_comment")
class PictureComment(
    var pictureId: Long,

    var userId: Long,

    var comment: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_comment_id")
    val id: Long? = null
): BaseTimeEntity()
