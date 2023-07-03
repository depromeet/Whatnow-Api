package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.config.s3.ImageFileExtension
import com.depromeet.whatnow.domains.image.domain.PromiseImageCommentType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Tag(name = "6. [이미지]")
@RequestMapping("/v1")
@SecurityRequirement(name = "access-token")
class ImageController(
    val getPresignedUrlUseCase: GetPresignedUrlUseCase,
    val successUseCase: ImageUploadSuccessUseCase,
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
        @RequestBody @Valid
        userLocation: CoordinateVo,
    ) {
        successUseCase.promiseUploadImageSuccess(promiseId, imageKey, promiseImageCommentType, userLocation)
    }

    @Operation(summary = "유저 프로필 이미지 업로드 성공 요청")
    @PostMapping("/images/{imageKey}/users/me")
    fun userUploadImageSuccess(@PathVariable imageKey: String) {
        successUseCase.userUploadImageSuccess(imageKey)
    }
}
