package com.depromeet.whatnow.domains.picture.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.events.domainEvent.PictureRegisterEvent
import javax.persistence.Column
import javax.persistence.Entity
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
