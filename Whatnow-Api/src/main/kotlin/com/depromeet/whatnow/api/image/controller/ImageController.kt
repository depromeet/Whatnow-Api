package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.config.s3.ImageFileExtension
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@Tag(name = "6. [이미지]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class ImageController(
    val getPresignedUrlUseCase: GetPresignedUrlUseCase,
) {
    @Operation(summary = "약속 관련 이미지 업로드 Presigned URL 발급")
    @GetMapping("/promises/{promiseId}/images")
    fun getPresignedUrlOfPromise(
        @PathVariable promiseId: Long,
        @RequestParam fileExtension: ImageFileExtension,
    ): ImageUrlResponse {
        return getPresignedUrlUseCase.forPromise(promiseId, fileExtension)
    }
}
