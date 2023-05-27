package com.depromeet.whatnow.domains.promiseuser.repository

import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PromiseUserRepository : JpaRepository<PromiseUser, Long>
