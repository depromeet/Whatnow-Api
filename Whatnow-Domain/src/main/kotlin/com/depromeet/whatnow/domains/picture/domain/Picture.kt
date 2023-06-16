package com.depromeet.whatnow.domains.picture.domain

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
@Table(name = "tbl_picture")
class Picture(
    var userId: Long,

    var promiseId: Long,

    var url: String,

    var uuid: String,

    @Enumerated(EnumType.STRING)
    var pictureType: PictureType,

    @Enumerated(EnumType.STRING)
    var pictureCommentType: PictureCommentType = PictureCommentType.NONE,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun createForPromise(userId: Long, promiseId: Long, url: String, uuid: String, pictureCommentType: PictureCommentType): Picture {
            return Picture(
                userId = userId,
                promiseId = promiseId,
                url = url,
                uuid = uuid,
                pictureType = PictureType.PROMISE,
                pictureCommentType = pictureCommentType,
            )
        }

        fun createForUser(userId: Long, url: String, uuid: String): Picture {
            return Picture(
                userId = userId,
                promiseId = 0,
                url = url,
                uuid = uuid,
                pictureType = PictureType.USER,
                pictureCommentType = PictureCommentType.NONE,
            )
        }
    }

    @PostPersist
    fun createPictureEvent() {
        Events.raise(PictureRegisterEvent(userId, promiseId))
    }
}
