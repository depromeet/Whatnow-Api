package com.depromeet.whatnow.domains.promiseHistory.domain

import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
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

    var prePromiseProgressId: Long?,

    var afterPromiseProgressId: Long?,

    var meetPlace: String?, // 만난장소에 대한 정보임. -> vo 로 만들어야 될 가능성 있음 (좌표,주소정보 필요)

    @ElementCollection(fetch = FetchType.LAZY)
    var avatarIds: MutableSet<Long> = mutableSetOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promise_history_id")
    val id: Long? = null,
)
