package com.depromeet.whatnow.domains.promiseprogress.adapter

import com.depromeet.whatnow.domains.promiseprogress.repository.PromiseProgressRepository
import org.springframework.stereotype.Component

@Component
class PromiseProgressAdapter(
    val promiseProgressRepository: PromiseProgressRepository,
)
