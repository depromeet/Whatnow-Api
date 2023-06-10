package com.depromeet.whatnow.domains.picture.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.events.domainEvent.PictureRegisterEvent
import javax.persistence.*

@Entity
@Table(name = "tbl_picture")
class Picture(
    var userId: Long,

    var promiseId: Long,

    var url: String,

    var uuid: String,

    @Enumerated(EnumType.STRING)
    var pictureCommentType: PictureCommentType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    @PostPersist
    fun createPictureEvent() {
        Events.raise(PictureRegisterEvent(userId, promiseId))
    }
}
