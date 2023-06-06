package com.depromeet.whatnow.domains.picture.service

import com.depromeet.whatnow.consts.IMAGE_DOMAIN
import com.depromeet.whatnow.domains.picture.adapter.PromiseAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PictureDomainService(
    val pictureAdapter: PromiseAdapter,
) {
    @Transactional
    fun successUploadImage(userId: Long, promiseId: Long, imageKey: String) {
        val imageUrl = IMAGE_DOMAIN + "promise/$promiseId/$imageKey"
        pictureAdapter.save(userId, promiseId, imageUrl, imageKey)
    }
}
