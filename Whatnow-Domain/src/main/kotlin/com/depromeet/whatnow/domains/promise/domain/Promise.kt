package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgressType
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_promise")
class Promise(

    @Column(updatable = true)
    var endTime : LocalDateTime = LocalDateTime.now(),

    // 약속명
    var title: String,

    var mainUserId: Long,

    @Embedded
    var meetPlace: PlaceVo?,

    @Enumerated
    var promiseProgreeType: PromiseProgressType,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_history_id")
    val id: Long? = null
) : BaseTimeEntity()