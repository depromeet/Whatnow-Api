package com.depromeet.whatnow.domains.image.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.exception.PromiseImageOwnershipMismatchException
import com.depromeet.whatnow.events.domainEvent.PromiseImageDeletedEvent
import com.depromeet.whatnow.events.domainEvent.PromiseImageRegisterEvent
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.PostRemove
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_image")
class PromiseImage(
    var userId: Long,

    var promiseId: Long,

    var uri: String,

    var imageKey: String,

    @Enumerated(EnumType.STRING)
    var fileExtension: ImageFileExtension,

    @Enumerated(EnumType.STRING)
    var promiseImageCommentType: PromiseImageCommentType,

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
            fileExtension: ImageFileExtension,
            promiseImageCommentType: PromiseImageCommentType,
        ): PromiseImage {
            return PromiseImage(userId, promiseId, uri, imageKey, fileExtension, promiseImageCommentType)
        }
    }

    @PostPersist
    fun createImageEvent() {
        Events.raise(PromiseImageRegisterEvent(userId, promiseId, imageKey))
    }

    fun validateOwnership(userId: Long) {
        if (this.userId != userId) {
            throw PromiseImageOwnershipMismatchException.EXCEPTION
        }
    }

    @PostRemove
    fun deletePromiseImage() {
        Events.raise(PromiseImageDeletedEvent(promiseId, imageKey, fileExtension))
    }
}
