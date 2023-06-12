package com.depromeet.whatnow.api.picture.controller

import com.depromeet.whatnow.api.picture.usecase.PictureUploadSuccessUseCase
import com.depromeet.whatnow.domains.picture.domain.PictureCommentType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "6. [이미지]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class PictureController(
    val successUseCase: PictureUploadSuccessUseCase,
) {

    @Operation(summary = "약속 관련 이미지 업로드 성공 요청")
    @PostMapping("/promises/{promiseId}/images/success/{imageKey}")
    fun successUploadImage(@PathVariable promiseId: Long, @PathVariable imageKey: String, @RequestParam pictureCommentType: PictureCommentType) {
        successUseCase.successUploadImage(promiseId, imageKey, pictureCommentType)
    }
}
