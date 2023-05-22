package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.common.vo.PlaceVo
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.PostPersist
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
    var meetPlace: PlaceVo?,

    @Enumerated
    var promiseType: PromiseType = PromiseType.BEFORE,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_history_id")
    val id: Long? = null,

//    차후 이벤트 domain 설계 되면 생성시에 Domain 계층에서 validate 하겠습니다
//    var promiseValidator: PromiseValidator,

) : BaseTimeEntity() {
    @PostPersist
    fun createPromise() {
        Events.raise(PromiseRegisterEvent.from(this))
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun delayPromise(endTime: LocalDateTime) {
        this.endTime = endTime
    }

    fun movePlace(placeVo: PlaceVo) {
        this.meetPlace = placeVo
    }

    fun endPromise() {
        this.promiseType = PromiseType.END
    }
    fun pendingPromise() {
        this.promiseType = PromiseType.PENDING
    }

    fun deletePromise() {
        this.promiseType = PromiseType.DELETED
    }

    fun updatePromiseProgressType(promiseType: PromiseType) {
        this.promiseType = promiseType
    }
}
