package com.depromeet.whatnow.domains.picture.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.picture.domain.Picture
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.repository.PictureRepository

@Adapter
class PictureAdapter(
    val pictureRepository: PictureRepository,
) {
    fun saveForPromise(userId: Long, promiseId: Long, imageUrl: String, imageKey: String, pictureCommentType: PictureCommentType): Picture {
        val picture = Picture.createForPromise(userId, promiseId, imageUrl, imageKey, pictureCommentType)
        return pictureRepository.save(picture)
    }

    fun saveForUser(userId: Long, imageUrl: String, imageKey: String): Picture {
        val picture = Picture.createForUser(userId, imageUrl, imageKey)
        return pictureRepository.save(picture)
    }
}
