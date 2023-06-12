package com.depromeet.whatnow.domains.progresshistory.domain

import com.depromeet.whatnow.domains.progresshistory.exception.ProgressIsSameException
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

    var currentPromiseProgress: PromiseProgress,

    var prePromiseProgress: PromiseProgress?,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_history_id")
    val id: Long? = null,
) {
    fun change(progress: PromiseProgress) {
        if (progress == currentPromiseProgress) {
            throw ProgressIsSameException.EXCEPTION
        }
        prePromiseProgress = currentPromiseProgress
        currentPromiseProgress = progress
    }

    companion object {
        fun of(progressId: Long, userId: Long): ProgressHistory {
            return ProgressHistory(progressId, userId, PromiseProgress.DEFAULT, null)
        }
    }
}
