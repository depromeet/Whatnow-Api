package com.depromeet.whatnow.domains.promiseProgress.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_progress")
class PromiseProgress(

    @Enumerated(EnumType.STRING)
    var type: PromiseProgressType,

    var state: String, // 상태 코드

    var kr: String, // 한글

    var img: String, // 이미지

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "promise_progress_id")
    val id: Long? = null,
)
