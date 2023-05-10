package com.depromeet.whatnowdomain.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity{

    @CreatedDate
    @Column(updatable = false)
    var createdAt : LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    @Column(updatable = true)
    var updatedAt : LocalDateTime = LocalDateTime.now()

}