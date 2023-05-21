package com.depromeet.whatnow.domains.promisehistory.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_history")
class PromiseHistory(

    var promiseId: Long,

    var userId: Long,

    @Enumerated(EnumType.STRING)
    var promiseHistoryType: PromiseHistoryType,

    var historyId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_history_id")
    val id: Long? = null,
) : BaseTimeEntity()
