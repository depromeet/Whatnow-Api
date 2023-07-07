package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.dto.ImageCommentElement
import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.api.image.usecase.ImageCommentReadUseCase
import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "6. [이미지]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class ImageController(
    val getPresignedUrlUseCase: GetPresignedUrlUseCase,
    val successUseCase: ImageUploadSuccessUseCase,
    val imageCommentReadUseCase: ImageCommentReadUseCase,
) {
    @Operation(summary = "약속 이미지 업로드 Presigned URL 발급")
    @GetMapping("/images/promises/{promiseId}")
    fun getPresignedUrlOfPromise(
        @PathVariable promiseId: Long,
        @RequestParam fileExtension: ImageFileExtension,
    ): ImageUrlResponse {
        return getPresignedUrlUseCase.forPromise(promiseId, fileExtension)
    }

    @Operation(summary = "유저 프로필 이미지 업로드 Presigned URL 발급")
    @GetMapping("/images/users/me")
    fun getPresignedUrlOfUser(
        @RequestParam fileExtension: ImageFileExtension,
    ): ImageUrlResponse {
        return getPresignedUrlUseCase.forUser(fileExtension)
    }

    @Operation(summary = "약속 이미지 업로드 성공 요청")
    @PostMapping("/images/{imageKey}/promises/{promiseId}")
    fun promiseUploadImageSuccess(
        @PathVariable promiseId: Long,
        @PathVariable imageKey: String,
        @RequestParam promiseImageCommentType: PromiseImageCommentType,
    ) {
        successUseCase.promiseUploadImageSuccess(promiseId, imageKey, promiseImageCommentType)
    }

    @Operation(summary = "유저 프로필 이미지 업로드 성공 요청")
    @PostMapping("/images/{imageKey}/users/me")
    fun userUploadImageSuccess(@PathVariable imageKey: String) {
        successUseCase.userUploadImageSuccess(imageKey)
    }

    @Operation(summary = "이미지 코멘트를 이넘으로 확인합니다. 주의!! 스웨거 이넘 예시 값과 실제 요청했을 때 값이 달라요 실제 요청값 기준으로 해주세요")
    @GetMapping("/images/comments")
    fun getImageCommentType(): List<ImageCommentElement> {
        return imageCommentReadUseCase.execute()
    }
}
