package com.depromeet.whatnow.domains.progresshistory.domain

import com.depromeet.whatnow.consts.DEFAULT_PROMISE_PROGRESS
import com.depromeet.whatnow.domains.progresshistory.exception.PromiseIsSameException
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
    fun change(progressId: Long) {
        if (progressId == currentPromiseProgressId) {
            throw PromiseIsSameException.EXCEPTION
        }
        currentPromiseProgressId?.let { // init default 이지 않을 때
            prePromiseProgressId = currentPromiseProgressId
        }
        currentPromiseProgressId = progressId
    }

    companion object {
        fun of(progressId: Long, userId: Long): ProgressHistory {
            return ProgressHistory(progressId, userId, DEFAULT_PROMISE_PROGRESS, null)
        }
    }
}
