package com.depromeet.whatnow.domains.progresshistory.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_progress_history")
class ProgressHistory(

    var promiseId: Long,

    var userId: Long,

    var prePromiseProgressId: Long?,

    var afterPromiseProgressId: Long?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_history_id")
    val id: Long? = null,
)
