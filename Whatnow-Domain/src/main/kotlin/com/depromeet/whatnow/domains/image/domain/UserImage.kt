package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_user_image")
class UserImage(
    var userId: Long,

    var uri: String,

    var imageKey: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun of(userId: Long, uri: String, imageKey: String): UserImage {
            return UserImage(userId, uri, imageKey)
        }
    }
}
