package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.exception.UserImageOwnershipMismatchException
import com.depromeet.whatnow.events.domainEvent.UserImageDeletedEvent
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostRemove
import javax.persistence.Table

@Entity
@Table(name = "tbl_user_image")
class UserImage(
    var userId: Long,

    var uri: String,

    var imageKey: String,

    @Enumerated(EnumType.STRING)
    var fileExtension: ImageFileExtension,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_image_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun of(userId: Long, uri: String, imageKey: String, fileExtension: ImageFileExtension): UserImage {
            return UserImage(userId, uri, imageKey, fileExtension)
        }
    }

    fun validateOwnership(userId: Long) {
        if (this.userId != userId) {
            throw UserImageOwnershipMismatchException.EXCEPTION
        }
    }

    @PostRemove
    fun deleteUserImage() {
        Events.raise(UserImageDeletedEvent(userId, imageKey, fileExtension))
    }
}
