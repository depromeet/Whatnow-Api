package com.depromeet.whatnow.domains.image.repository

import com.depromeet.whatnow.domains.image.domain.PromiseImage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PromiseImageRepository : JpaRepository<PromiseImage, Long> {
    fun findAllByPromiseId(promiseId: Long): List<PromiseImage>
    fun findByImageKey(imageKey: String): PromiseImage
}
