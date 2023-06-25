package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.image.domain.Image
import com.depromeet.whatnow.domains.image.domain.ImageCommentType
import com.depromeet.whatnow.domains.image.repository.ImageRepository

@Adapter
class ImageAdapter(
    val imageRepository: ImageRepository,
) {
    fun saveForPromise(userId: Long, promiseId: Long, imageUrl: String, imageKey: String, imageCommentType: ImageCommentType): Image {
        val image = Image.createForPromise(userId, promiseId, imageUrl, imageKey, imageCommentType)
        return imageRepository.save(image)
    }

    fun saveForUser(userId: Long, imageUrl: String, imageKey: String): Image {
        val image = Image.createForUser(userId, imageUrl, imageKey)
        return imageRepository.save(image)
    }
}
