package com.depromeet.whatnow.api.promiseprogress.controller

import com.depromeet.whatnow.api.promiseprogress.dto.response.PromiseProgressGroupElement
import com.depromeet.whatnow.api.promiseprogress.usecase.PromiseProgressChangeUseCase
import com.depromeet.whatnow.api.promiseprogress.usecase.PromiseProgressReadUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/promises")
@Tag(name = "5(일단 5번으로 잡음 순서). [약속진행상태]")
@SecurityRequirement(name = "access-token")
class PromiseProgressController(
    val promiseProgressReadUseCase: PromiseProgressReadUseCase,
    val promiseProgressChangeUseCase: PromiseProgressChangeUseCase
) {

    @GetMapping("/progresses")
    fun getPromiseProgresses(): List<PromiseProgressGroupElement> {
        return promiseProgressReadUseCase.execute()
    }

    @PostMapping("/{promiseId}/progress/{progressCode}")
    fun changePromiseProgress(@PathVariable progressCode: String, @PathVariable promiseId: String): List<PromiseProgressGroupElement> {
        return promiseProgressChangeUseCase.execute(promiseId,progressCode)
    }
}
