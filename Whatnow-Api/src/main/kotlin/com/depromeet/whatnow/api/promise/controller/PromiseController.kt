package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.usecase.PromiseRegisterUseCase
import com.depromeet.whatnow.common.vo.PlaceVo
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

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
    @Operation(
        summary = "약속(promise) 수정",
        description = "약속을 수정합니다. (약속 제목, 약속 장소, 약속 시간)",
    )
    @PutMapping("/promises/location/{promise-id}")
    fun updatePromiseLocation(@RequestParam(value = "promise") promiseId: Long, @RequestBody meetPlace: PlaceVo): PromiseDto {
        return promiseRegisterUseCase.updatePromiseMeetPlace(promiseId, meetPlace)
    }

//    2. 약속 시간 수정
//    3. 약속 제목 수정
//    4. 약속 삭제
}
