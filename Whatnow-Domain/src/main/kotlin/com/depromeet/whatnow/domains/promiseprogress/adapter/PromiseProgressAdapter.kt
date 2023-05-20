package com.depromeet.whatnow.domains.promiseprogress.adapter

import com.depromeet.whatnow.domains.promiseprogress.domain.PromiseProgress
import com.depromeet.whatnow.domains.promiseprogress.repository.PromiseProgressRepository
import org.springframework.stereotype.Component

@Component
class PromiseProgressAdapter(
    val promiseProgressRepository: PromiseProgressRepository,
) {
    fun findAll(): List<PromiseProgress> {
        return promiseProgressRepository.findAll()
    }
}
