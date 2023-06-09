package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.dto.PromiseSplitedByPromiseTypeDto
import com.depromeet.whatnow.api.promise.usecase.PromiseRegisterUseCase
import com.depromeet.whatnow.common.vo.PlaceVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("/v1")
@Tag(name = "3. [약속]")
@SecurityRequirement(name = "access-token")
class PromiseController(
    val promiseRegisterUseCase: PromiseRegisterUseCase,
) {
    @Operation(summary = "약속(promise) 생성", description = "약속을 생성합니다.")
    @PostMapping("/promises")
    fun createPromise(@RequestBody promiseRequest: PromiseRequest): PromiseDto {
        return promiseRegisterUseCase.createPromise(promiseRequest)
    }

//    1. 약속 장소 변경
    @Operation(summary = "약속(promise) 장소 수정", description = "약속 장소를 변경합니다.")
    @PutMapping("/promises/{promise-id}/location")
    fun updatePromiseLocation(@RequestParam(value = "promise-id") promiseId: Long, @RequestBody meetPlace: PlaceVo): PromiseDto {
        return promiseRegisterUseCase.updatePromiseMeetPlace(promiseId, meetPlace)
    }

    @Operation(summary = "약속(promise)시간 수정", description = "약속을 수정합니다. (약속 제목, 약속 장소, 약속 시간)")
    @PutMapping("/promises/{promise-id}/end-times/{end-time}")
    fun updatePromiseEndTime(@RequestParam(value = "promise-id") promiseId: Long, @RequestParam(value = "end-time") endTime: LocalDateTime): PromiseDto {
        return promiseRegisterUseCase.updatePromiseEndTime(promiseId, endTime)
    }

//    나의 약속 전부 조회
    @Operation(summary = "나의 약속 전부 조회", description = "유저의 약속 전부 조회 (단, 예정된 약속과 지난 약속을 구분해서 조회")
    @GetMapping("/promises/users/{user-id}/")
    fun findByPromiseByUser(@RequestParam(value = "user-id") userId: Long): List<PromiseSplitedByPromiseTypeDto> {
        return promiseRegisterUseCase.findPromiseByUserIdSeparatedType(userId)
    }

//    일단위 약속 조회
//    월단위 약속 조회
//    약속 취소
//    3. 약속 제목 수정

//    4. 약속 삭제
}
