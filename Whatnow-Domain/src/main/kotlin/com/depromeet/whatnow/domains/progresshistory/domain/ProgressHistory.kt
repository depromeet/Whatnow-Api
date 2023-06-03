package com.depromeet.whatnow.domains.progresshistory.domain

import com.depromeet.whatnow.consts.DEFAULT_PROMISE_PROGRESS
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

    var currentPromiseProgressId: Long?,

    var prePromiseProgressId: Long?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_history_id")
    val id: Long? = null,
) {
    fun change(progressCode: Long) {
        currentPromiseProgressId?.let { // init default 이지 않을 때
            prePromiseProgressId = currentPromiseProgressId
        }
        currentPromiseProgressId = progressCode
    }

    companion object {
        fun of(progressId: Long, userId: Long): ProgressHistory {
            return ProgressHistory(progressId, userId, DEFAULT_PROMISE_PROGRESS, null)
        }
    }
}
