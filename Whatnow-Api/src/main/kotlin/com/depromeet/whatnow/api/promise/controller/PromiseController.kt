package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.usecase.PromiseRegisterUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
@Tag(name = "3. [약속진행상태]")
@SecurityRequirement(name = "access-token")
class PromiseController(
    val promiseRegisterUseCase: PromiseRegisterUseCase,
) {
    @Operation(summary = "약속(promise) 생성", description = "약속을 생성합니다.")
    @PostMapping("/promise")
    fun createPromise(@RequestBody promiseRequest: PromiseRequest): PromiseDto {
        return promiseRegisterUseCase.createPromise(promiseRequest)
    }
}
