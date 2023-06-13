package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.annotation.RequiresMainUser
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.usecase.PromiseReadUseCase
import com.depromeet.whatnow.api.promise.usecase.PromiseRegisterUseCase
import com.depromeet.whatnow.common.vo.PlaceVo
import com.depromeet.whatnow.domains.promise.domain.PromiseType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    val promiseReadUseCase: PromiseReadUseCase,
) {
    //    나의 약속 전부 조회
    @Operation(summary = "나의 약속 전부 조회", description = "유저의 약속 전부 조회 (단, 예정된 약속과 지난 약속을 구분해서 조회")
    // view 방식에 따라 구분하게 기능 추가 예정
    @GetMapping("/promises/users/separated")
    fun findByPromiseByUser(): Map<PromiseType, MutableList<PromiseFindDto>> {
        return promiseReadUseCase.findPromiseByUserIdSeparatedType()
    }

    @Operation(summary = "월단위 약속 조회", description = "유저의 월간 약속 조회 (단, 예정된 약속과 지난 약속을 구분없이 조회)")
    @GetMapping("/promises/users")
    fun findByPromiseByUserAndYearMonth(@RequestParam(value = "year-month") yearMonth: String): List<PromiseFindDto> {
        return promiseReadUseCase.findPromiseByUserIdYearMonth(yearMonth)
    }

    @Operation(summary = "약속(promise) 생성", description = "약속을 생성합니다.")
    @PostMapping("/promises")
    fun createPromise(@RequestBody promiseRequest: PromiseRequest): PromiseDto {
        return promiseRegisterUseCase.createPromise(promiseRequest)
    }

//    1. 약속 장소 변경
    @Operation(summary = "약속(promise) 장소 수정", description = "약속 장소를 변경합니다.")
    @RequiresMainUser
    @PutMapping("/promises/{promise-id}/location")
    fun updatePromiseLocation(@PathVariable(value = "promise-id") promiseId: Long, @RequestBody meetPlace: PlaceVo): PromiseDto {
        return promiseRegisterUseCase.updatePromiseMeetPlace(promiseId, meetPlace)
    }

    @Operation(summary = "약속(promise)시간 수정", description = "약속을 수정합니다. (약속 제목, 약속 장소, 약속 시간)")
    @RequiresMainUser
    @PutMapping("/promises/{promise-id}/end-times")
    fun updatePromiseEndTime(
        @PathVariable(value = "promise-id") promiseId: Long,
        @RequestBody
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        endTime: LocalDateTime,
    ): PromiseDto {
        return promiseRegisterUseCase.updatePromiseEndTime(promiseId, endTime)
    }

    // 약속 취소
    @Operation(summary = "약속 취소", description = "약속을 취소합니다.")
    @RequiresMainUser
    @DeleteMapping("/promises/{promise-id}")
    fun deletePromise(@PathVariable(value = "promise-id") promiseId: Long) {
        promiseRegisterUseCase.deletePromise(promiseId)
    }
}