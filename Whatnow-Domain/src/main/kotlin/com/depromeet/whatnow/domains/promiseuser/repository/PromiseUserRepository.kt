package com.depromeet.whatnow.domains.promiseuser.repository

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PromiseUserRepository : JpaRepository<PromiseUser, Long> {
    fun findByPromiseId(promiseId: Long): List<PromiseUser>?
    fun findByUserId(userId: Long): List<PromiseUser>?
    fun findByPromiseIdAndUserId(promiseId: Long, userId: Long): PromiseUser?

    fun findByPromiseIdIn(promiseIds: List<Long>): List<PromiseUser>?

    @Query("SELECT DISTINCT p FROM PromiseUser p WHERE p.promiseId IN :promiseIds")
    fun findByUniquePromiseIdIn(promiseIds: List<Long>): Set<PromiseUser>
}
