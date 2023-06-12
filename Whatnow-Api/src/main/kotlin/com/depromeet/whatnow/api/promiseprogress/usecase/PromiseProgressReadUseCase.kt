package com.depromeet.whatnow.api.promiseprogress.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress

@UseCase
class PromiseProgressReadUseCase() {
    fun execute(): List<PromiseProgressGroupElement> {
        return PromiseProgress.values().groupBy {
                p ->
            p.progressGroup
        }.map {
                (k, value) ->
            PromiseProgressGroupElement(k, value)
        }
    }
}
