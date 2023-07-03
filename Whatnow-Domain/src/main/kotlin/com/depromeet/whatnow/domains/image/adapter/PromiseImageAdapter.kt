package com.depromeet.whatnow.domains.image.adapter

import com.depromeet.whatnow.annotation.Adapter
import com.depromeet.whatnow.domains.image.domain.PromiseImage
import com.depromeet.whatnow.domains.image.repository.PromiseImageRepository

@Adapter
class PromiseImageAdapter(
    val promiseImageRepository: PromiseImageRepository,
) {
    fun save(promiseImage: PromiseImage): PromiseImage {
        return promiseImageRepository.save(promiseImage)
    }
}
