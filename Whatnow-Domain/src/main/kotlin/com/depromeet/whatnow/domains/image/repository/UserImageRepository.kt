package com.depromeet.whatnow.domains.image.repository

import com.depromeet.whatnow.domains.image.domain.UserImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserImageRepository : JpaRepository<UserImage, Long>
