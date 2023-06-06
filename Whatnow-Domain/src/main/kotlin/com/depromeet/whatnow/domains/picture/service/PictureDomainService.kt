package com.depromeet.whatnow.domains.picture.service

import com.depromeet.whatnow.domains.picture.adapter.PromiseAdapter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PictureDomainService(
    val pictureAdapter: PromiseAdapter,
) {
    @Transactional
    fun successUploadImage(userId: Long, promiseId: Long, imageKey: String) {
        pictureAdapter.save(userId, promiseId, imageKey)
    }
}
