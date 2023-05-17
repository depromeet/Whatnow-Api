package com.depromeet.whatnow.domains.promiseProgress.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_progress")
class PromiseProgress(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promise_progress_id")
    val id: Long? = null,
)
