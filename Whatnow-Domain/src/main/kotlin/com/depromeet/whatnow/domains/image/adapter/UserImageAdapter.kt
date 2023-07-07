package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.image.domain.UserImage
import com.depromeet.whatnow.domains.image.exception.UserImageNotFoundException
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

    fun findByUserIdAndImageKey(userId: Long, imageKey: String): UserImage {
        return userImageRepository.findByUserIdAndImageKey(userId, imageKey) ?: run { throw UserImageNotFoundException.EXCEPTION }
    }

    fun deleteByImageKeyAndUserId(imageKey: String, userId: Long) {
        return userImageRepository.deleteByImageKeyAndUserId(imageKey, userId)
    }
}
