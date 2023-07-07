package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.image.domain.UserImage
import com.depromeet.whatnow.domains.image.repository.UserImageRepository

@Adapter
class UserImageAdapter(
    val userImageRepository: UserImageRepository,
) {
    fun save(userImage: UserImage): UserImage {
        return userImageRepository.save(userImage)
    }

    fun findAllByUserId(userId: Long): List<UserImage> {
        return userImageRepository.findAllByUserId(userId)
    }
}
