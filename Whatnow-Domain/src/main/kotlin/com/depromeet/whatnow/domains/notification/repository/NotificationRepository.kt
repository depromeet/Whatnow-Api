package com.depromeet.whatnow.domains.notification.repository

import com.depromeet.whatnow.domains.notification.domain.Notification
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findAllByTargetUserIdOrderByCreatedAtDesc(targetUserId: Long, pageable: Pageable): Slice<Notification>

    @Query(
        "SELECT * " +
            "FROM tbl_notification " +
            "WHERE promise_id = :promiseId AND target_user_id = :userId AND notification_type IN ('ARRIVAL') " +
            "ORDER BY created_at DESC",
        nativeQuery = true,
    )
    fun findAllHighlights(promiseId: Long, userId: Long, pageable: Pageable): Slice<Notification>

    @Query(
        "SELECT * " +
            "FROM tbl_notification " +
            "WHERE promise_id = :promiseId AND notification_type IN ('ARRIVAL') " +
            "ORDER BY created_at",
        nativeQuery = true,
    )
    fun findAllHighlightsTop3(promiseId: Long): List<Notification>
}
