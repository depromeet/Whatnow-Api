package com.depromeet.whatnow.domains.promiseuser.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.events.domainEvent.PromiseUserCancelEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUserRegisterEvent
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
import javax.persistence.PostUpdate
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_user")
class PromiseUser(
    var promiseId: Long,

    var userId: Long,

    @Embedded
    var userLocation: CoordinateVo? = null,

    @Embedded
    var promiseUserType: PromiseUserType? = PromiseUserType.READY,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_user_id")
    val id: Long? = null,

) : BaseTimeEntity() {

    @PostUpdate
    fun cancelPromiseUser() {
        this.promiseUserType = PromiseUserType.CANCEL
        Events.raise(PromiseUserCancelEvent(this.promiseId, this.userId, this.id!!))
    }

    @PostPersist
    fun createPromiseUserEvent() {
        Events.raise(PromiseUserRegisterEvent(this.promiseId, this.userId, this.id!!))
    }

    // wait 으로 상태 변경
    fun updatePromiseUserTypeToWait() {
        this.promiseUserType = PromiseUserType.WAIT
    }

    fun updatePromiseUserTypeToLate() {
        this.promiseUserType = PromiseUserType.LATE
    }

    fun updatePromiseUserType(promiseUserType: PromiseUserType?) {
        this.promiseUserType = promiseUserType
    }
}
