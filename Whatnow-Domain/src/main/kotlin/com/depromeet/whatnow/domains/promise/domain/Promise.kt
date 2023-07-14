package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.events.domainEvent.PromiseCancelEvent
import com.depromeet.whatnow.events.domainEvent.PromiseRegisterEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUpdateEndTimeEvent
import com.depromeet.whatnow.events.domainEvent.PromiseUpdateMeetPlaceEvent
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise")
class Promise(

    @Column(updatable = true)
    var endTime: LocalDateTime,

    // 약속명
    var title: String,

    var mainUserId: Long,

    @Embedded
    var meetPlace: PlaceVo? = null,

    @Enumerated(EnumType.STRING)
    var promiseType: PromiseType = PromiseType.BEFORE,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_history_id")
    val id: Long? = null,

//    차후 이벤트 domain 설계 되면 생성시에 Domain 계층에서 validate 하겠습니다
//    var promiseValidator: PromiseValidator,
) : BaseTimeEntity() {
    fun createPromiseEvent() {
        Events.raise(PromiseRegisterEvent(this.id!!, this.mainUserId))
    }
    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateEndTime(endTime: LocalDateTime) {
        this.endTime = endTime
        pendingPromise()
    }
    fun delayPromise(endTime: LocalDateTime) {
        this.endTime = endTime
    }

    fun movePlace(placeVo: PlaceVo) {
        this.meetPlace = placeVo
    }

    fun endPromise() {
        this.promiseType = PromiseType.END
        Events.raise(PromiseUpdateEndTimeEvent(this.id!!, this.endTime))
    }

    fun pendingPromise() {
        this.promiseType = PromiseType.BEFORE
    }

    fun deletePromise() {
        this.promiseType = PromiseType.DELETED
        Events.raise(PromiseCancelEvent(this.id!!))
    }

    fun updatePromiseProgressType(promiseType: PromiseType) {
        this.promiseType = promiseType
    }

    fun updateMeetPlace(meetPlace: PlaceVo) {
        this.meetPlace = meetPlace
        Events.raise(PromiseUpdateMeetPlaceEvent(this.id!!, this.meetPlace!!))
    }

    fun isActive(): Boolean {
        val currentTime = LocalDateTime.now()
        return this.endTime.minusHours(1) < currentTime && currentTime < this.endTime.plusMinutes(30)
    }

    fun isBeforePromiseEndTime(): Boolean {
        return this.endTime > LocalDateTime.now()
    }

    fun isAfterPromiseEndTime(): Boolean {
        return this.endTime < LocalDateTime.now()
    }
}
