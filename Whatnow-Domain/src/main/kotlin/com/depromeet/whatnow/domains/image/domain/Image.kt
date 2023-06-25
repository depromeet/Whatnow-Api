package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.events.domainEvent.PictureRegisterEvent
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
@Table(name = "tbl_image")
class Image(
    var userId: Long,

    var promiseId: Long,

    var uri: String,

    var imageKey: String,

    @Enumerated(EnumType.STRING)
    var imageType: ImageType,

    @Enumerated(EnumType.STRING)
    var imageCommentType: ImageCommentType = ImageCommentType.NONE,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun createForPromise(userId: Long, promiseId: Long, uri: String, imageKey: String, imageCommentType: ImageCommentType): Image {
            return Image(
                userId = userId,
                promiseId = promiseId,
                uri = uri,
                imageKey = imageKey,
                imageType = ImageType.PROMISE,
                imageCommentType = imageCommentType,
            )
        }

        fun createForUser(userId: Long, uri: String, imageKey: String): Image {
            return Image(
                userId = userId,
                promiseId = 0,
                uri = uri,
                imageKey = imageKey,
                imageType = ImageType.USER,
                imageCommentType = ImageCommentType.NONE,
            )
        }
    }

    @PostPersist
    fun createPictureEvent() {
        Events.raise(PictureRegisterEvent(userId, promiseId))
    }
}
