package com.depromeet.whatnow.domains.notification.domain

import com.depromeet.whatnow.common.BaseTimeEntity
import javax.persistence.Column
import javax.persistence.DiscriminatorColumn
import javax.persistence.DiscriminatorType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table

@Entity
@Table(name = "tbl_notification")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
abstract class Notification(
    open val targetUserId: Long,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    open val id: Long? = null,
) : BaseTimeEntity()
