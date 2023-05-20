package com.depromeet.whatnow.api.promiseprogress.controller

import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.api.promiseprogress.usecase.PromiseProgressReadUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/promise/progress")
@Tag(name = "5(일단 5번으로 잡음 순서). [약속진행 상태 조회]")
@SecurityRequirement(name = "access-token")
class PromiseProgressController(
    val promiseProgressReadUseCase: PromiseProgressReadUseCase,
) {

    @GetMapping
    fun getPromiseProgresses(): List<PromiseProgressGroupElement> {
        return promiseProgressReadUseCase.execute()
    }
}
