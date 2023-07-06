package com.depromeet.whatnow.api.image.controller

import com.depromeet.whatnow.api.image.dto.ImageCommentElement
import com.depromeet.whatnow.api.image.dto.ImageUrlResponse
import com.depromeet.whatnow.api.image.dto.PromiseImageDetailResponse
import com.depromeet.whatnow.api.image.dto.PromiseImageResponse
import com.depromeet.whatnow.api.image.usecase.GetPresignedUrlUseCase
import com.depromeet.whatnow.api.image.usecase.ImageCommentReadUseCase
import com.depromeet.whatnow.api.image.usecase.ImageUploadSuccessUseCase
import com.depromeet.whatnow.api.image.usecase.PromiseImageReadUseCase
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
@RequestMapping("/v1/images")
@SecurityRequirement(name = "access-token")
class ImageController(
    val getPresignedUrlUseCase: GetPresignedUrlUseCase,
    val successUseCase: ImageUploadSuccessUseCase,
    val imageCommentReadUseCase: ImageCommentReadUseCase,
    val promiseImageReadUseCase: PromiseImageReadUseCase,
) {
    @Tag(name = "6-1 [약속 이미지]")
    @Operation(summary = "약속 이미지 업로드 Presigned URL 발급")
    @GetMapping("/promises/{promiseId}/presigned-url")
    fun getPresignedUrlOfPromise(
        @PathVariable promiseId: Long,
        @RequestParam fileExtension: ImageFileExtension,
    ): ImageUrlResponse {
        return getPresignedUrlUseCase.forPromise(promiseId, fileExtension)
    }

    @Tag(name = "6-1 [약속 이미지]")
    @Operation(summary = "약속 이미지 업로드 성공 요청")
    @PostMapping("/{imageKey}/promises/{promiseId}")
    fun promiseUploadImageSuccess(
        @PathVariable promiseId: Long,
        @PathVariable imageKey: String,
        @RequestParam promiseImageCommentType: PromiseImageCommentType,
        @RequestBody @Valid
        userLocation: CoordinateVo,
    ) {
        successUseCase.promiseUploadImageSuccess(promiseId, imageKey, promiseImageCommentType, userLocation)
    }

    @Tag(name = "6-1 [약속 이미지]")
    @Operation(summary = "이미지 코멘트를 이넘으로 확인합니다. 주의!! 스웨거 이넘 예시 값과 실제 요청했을 때 값이 달라요 실제 요청값 기준으로 해주세요")
    @GetMapping("/comments")
    fun getImageCommentType(): List<ImageCommentElement> {
        return imageCommentReadUseCase.execute()
    }

    @Tag(name = "6-1 [약속 이미지]")
    @Operation(summary = "약속 아이디를 통해 모든 이미지를 LATE, WAIT로 구분하여 가져옵니다.")
    @GetMapping("/promises/{promiseId}")
    fun getLateAndWaitImagesByPromiseId(@PathVariable promiseId: Long): PromiseImageResponse {
        return promiseImageReadUseCase.getLateAndWaitImagesByPromiseId(promiseId)
    }

    @Tag(name = "6-1 [약속 이미지]")
    @Operation(summary = "이미지 키를 통해 이미지를 가져옵니다.")
    @GetMapping("/{imageKey}")
    fun getImageByImageKey(@PathVariable imageKey: String): PromiseImageDetailResponse {
        return promiseImageReadUseCase.getImageByImageKey(imageKey)
    }

    @Tag(name = "6-2 [유저 이미지]")
    @Operation(summary = "유저 프로필 이미지 업로드 Presigned URL 발급")
    @GetMapping("/users/me/presigned-url")
    fun getPresignedUrlOfUser(
        @RequestParam fileExtension: ImageFileExtension,
    ): ImageUrlResponse {
        return getPresignedUrlUseCase.forUser(fileExtension)
    }

    @Tag(name = "6-2 [유저 이미지]")
    @Operation(summary = "유저 프로필 이미지 업로드 성공 요청")
    @PostMapping("/{imageKey}/users/me")
    fun userUploadImageSuccess(@PathVariable imageKey: String) {
        successUseCase.userUploadImageSuccess(imageKey)
    }
}
