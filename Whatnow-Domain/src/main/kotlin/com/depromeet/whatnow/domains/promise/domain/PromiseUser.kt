package com.depromeet.whatnow.domains.promise.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import com.depromeet.whatnow.common.vo.CoordinateVo
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_promise_user")
class PromiseUser(
    var promiseId: Long,

    var userId: Long,

    @Embedded
    var userLocation: CoordinateVo?,

    var userState: String,

    // 유저 약속 한줄 상태 : 디폴트로 넣어줘야 할 것 같은데 뭘 넣을지 아직 못정하겠어요.
    var type: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promise_user_id")
    val id: Long? = null,
) : BaseTimeEntity()
