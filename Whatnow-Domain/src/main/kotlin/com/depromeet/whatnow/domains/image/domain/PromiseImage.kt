package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.events.domainEvent.PromiseImageRegisterEvent
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_image")
class PromiseImage(
    var userId: Long,

    var promiseId: Long,

    var uri: String,

    var imageKey: String,

    @Enumerated(EnumType.STRING)
    var promiseImageCommentType: PromiseImageCommentType,

    @Embedded
    var userLocation: CoordinateVo? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_image_id")
    val id: Long? = null,
) : BaseTimeEntity() {
    companion object {
        fun of(
            userId: Long,
            promiseId: Long,
            uri: String,
            imageKey: String,
            promiseImageCommentType: PromiseImageCommentType,
            userLocation: CoordinateVo,
        ): PromiseImage {
            return PromiseImage(userId, promiseId, uri, imageKey, promiseImageCommentType, userLocation)
        }
    }

    @PostPersist
    fun createImageEvent() {
        Events.raise(PromiseImageRegisterEvent(userId, promiseId))
    }
}
