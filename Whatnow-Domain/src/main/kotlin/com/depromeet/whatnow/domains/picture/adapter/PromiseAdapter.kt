package com.depromeet.whatnow.domains.picture.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.picture.domain.Picture
import com.depromeet.whatnow.domains.picture.repository.PictureRepository

@Adapter
class PromiseAdapter(
    val pictureRepository: PictureRepository,
) {
    fun save(userId: Long, promiseId: Long, imageUrl: String, imageKey: String) {
        val picture = Picture(userId, promiseId, imageUrl, imageKey)
        pictureRepository.save(picture)
    }
}
