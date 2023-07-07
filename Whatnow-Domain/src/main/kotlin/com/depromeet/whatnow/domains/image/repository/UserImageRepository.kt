package com.depromeet.whatnow.domains.image.repository

import com.depromeet.whatnow.domains.image.domain.UserImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserImageRepository : JpaRepository<UserImage, Long> {
    fun findAllByUserId(userId: Long): List<UserImage>
    fun findByUserIdAndImageKey(userId: Long, imageKey: String): UserImage?
    fun deleteByImageKeyAndUserId(imageKey: String, userId: Long)
}
