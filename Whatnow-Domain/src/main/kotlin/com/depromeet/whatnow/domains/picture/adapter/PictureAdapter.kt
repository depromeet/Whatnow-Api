package com.depromeet.whatnow.domains.picture.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.picture.domain.Picture
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import com.depromeet.whatnow.domains.picture.repository.PictureRepository

@Adapter
class PictureAdapter(
    val pictureRepository: PictureRepository,
) {
    fun save(userId: Long, promiseId: Long, imageUrl: String, imageKey: String, pictureCommentType: PictureCommentType) {
        val picture = Picture(userId, promiseId, imageUrl, imageKey, pictureCommentType)
        pictureRepository.save(picture)
    }
}
