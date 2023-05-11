package com.depromeet.whatnow.domains.user.repository

import com.depromeet.whatnow.domains.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>
