package com.depromeet.whatnow.api.image.usecase

import com.depromeet.whatnow.annotation.UseCase
import com.depromeet.whatnow.api.image.dto.ImageCommentElement
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType

@UseCase
class ImageCommentReadUseCase {
    fun execute(): List<ImageCommentElement> {
        return PromiseImageCommentType.values().groupBy {
                p ->
            p.promiseUserType
        }.map {
                (k, value) ->
            ImageCommentElement(k, value)
        }
    }
}
