package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.exception.PromiseImageNotFoundException
import com.depromeet.whatnow.domains.image.repository.PromiseImageRepository

@Adapter
class PromiseImageAdapter(
    val promiseImageRepository: PromiseImageRepository,
) {
    fun save(promiseImage: PromiseImage): PromiseImage {
        return promiseImageRepository.save(promiseImage)
    }

    fun findAllByPromiseId(promiseId: Long): List<PromiseImage> {
        return promiseImageRepository.findAllByPromiseId(promiseId)
    }

    fun findByImageKey(imageKey: String): PromiseImage {
        return promiseImageRepository.findByImageKey(imageKey) ?: run { throw PromiseImageNotFoundException.EXCEPTION }
    }

    fun deleteByImageKeyAndPromiseId(imageKey: String, promiseId: Long) {
        promiseImageRepository.deleteByPromiseIdAndImageKey(promiseId, imageKey)
    }

    fun findByPromiseIdAndImageKey(promiseId: Long, imageKey: String): PromiseImage {
        return promiseImageRepository.findByPromiseIdAndImageKey(promiseId, imageKey)
            ?: run { throw PromiseImageNotFoundException.EXCEPTION }
    }
}
