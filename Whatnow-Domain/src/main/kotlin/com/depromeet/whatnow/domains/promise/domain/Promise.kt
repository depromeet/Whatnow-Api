package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgressGroup
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
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
    var meetPlace: PlaceVo?,

    @Enumerated
    var promiseProgreeType: PromiseProgressGroup,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_history_id")
    val id: Long? = null,
) : BaseTimeEntity()
