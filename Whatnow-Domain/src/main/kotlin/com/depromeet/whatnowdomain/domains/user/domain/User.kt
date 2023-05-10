package com.depromeet.whatnowdomain.domains.user.domain

import com.depromeet.whatnowdomain.common.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "tbl_user")
class User (
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id : Long ,


        ) : BaseTimeEntity(){
}