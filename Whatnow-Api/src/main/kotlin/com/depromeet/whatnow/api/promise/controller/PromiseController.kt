package com.depromeet.whatnow.api.promise.controller

import com.depromeet.whatnow.api.promise.annotation.RequiresMainUser
import com.depromeet.whatnow.api.promise.dto.PromiseCreateDto
import com.depromeet.whatnow.api.promise.dto.PromiseDetailDto
import com.depromeet.whatnow.api.promise.dto.PromiseDto
import com.depromeet.whatnow.api.promise.dto.PromiseFindDto
import com.depromeet.whatnow.api.promise.dto.PromiseRequest
import com.depromeet.whatnow.api.promise.usecase.InviteCodeReadUseCase
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
import java.time.YearMonth

@RestController
@RequestMapping("/v1")
@Tag(name = "3. [약속]")
@SecurityRequirement(name = "access-token")
class PromiseController(
    val promiseRegisterUseCase: PromiseRegisterUseCase,
    val promiseReadUseCase: PromiseReadUseCase,
    val inviteCodeReadUseCase: InviteCodeReadUseCase,
) {
    @Deprecated("나의 약속 전부 조회", replaceWith = ReplaceWith("findPromiseByStatus"))
    @Operation(summary = "나의 약속 전부 조회", description = "유저의 약속 전부 조회 (단, 예정된 약속과 지난 약속을 구분해서 조회")
    // view 방식에 따라 구분하게 기능 추가 예정
    @GetMapping("/promises/users/separated")
    fun findPromiseByUser(): Map<PromiseType, MutableList<PromiseFindDto>> {
        return promiseReadUseCase.findPromiseByUserIdSeparatedType()
    }

    @Operation(
        summary = "월단위 약속 조회",
        description = "유저의 월간 약속 조회 (단, 예정된 약속과 지난 약속을 구분없이 조회), year-month 파라미터는 2021-01 이 형식입니다.",
    )
    @GetMapping("/promises/users")
    fun findPromiseByUserAndYearMonth(@RequestParam(value = "year-month") yearMonth: YearMonth): List<PromiseFindDto> {
        return promiseReadUseCase.findPromiseByUserIdYearMonth(yearMonth)
    }

    @Operation(summary = "약속 모음집 상세", description = "D3. 지난(AFTER) 약속 상세 조회 (날짜(월,일), 약속 사진 url, 타임오버 캡쳐, 만난 사람(프로필 사진 Url,  이름, wait/late 여부, interaction 종륲별 카운트), 하이라이트 기록 최대 3개")
    @GetMapping("/promises/users/status/{status}")
    fun findPromiseDetailByStatus(@PathVariable(value = "status") status: PromiseType): List<PromiseDetailDto> {
        return promiseReadUseCase.findPromiseDetailByStatus(status)
    }

    @Operation(summary = "현재 약속이 활성화 상태인지 비활성화 상태인지 조회", description = "현재 약속이 활성화 상태인지 비활성화 상태인지 조회한다.")
    @GetMapping("/promises/{promise-id}/active")
    fun findPromiseActive(@PathVariable(value = "promise-id") promiseId: Long): Boolean {
        return promiseReadUseCase.findPromiseActive(promiseId)
    }

    @Operation(summary = "promiseId 로 약속 조회", description = "promiseId 로 약속 조회")
    @GetMapping("/promises/{promise-id}")
    fun findByPromiseId(@PathVariable(value = "promise-id") promiseId: Long): PromiseFindDto {
        return promiseReadUseCase.findByPromiseId(promiseId)
    }

    @Operation(summary = "promiseId 로 약속 초대 코드 조회", description = "promiseId 로 약속 초대 코드 조회하기")
    @GetMapping("/promises/{promise-id}/invite-codes")
    fun findInviteCodeByPromiseId(@PathVariable(value = "promise-id") promiseId: Long): String {
        return inviteCodeReadUseCase.findInviteCodeByPromiseId(promiseId)
    }

    @Operation(summary = "약속(promise) 생성", description = "약속을 생성합니다.")
    @PostMapping("/promises")
    fun createPromise(@RequestBody promiseRequest: PromiseRequest): PromiseCreateDto {
        return promiseRegisterUseCase.createPromise(promiseRequest)
    }

    @Operation(summary = "약속(promise) 장소 수정", description = "약속 장소를 변경합니다.")
    @RequiresMainUser
    @PutMapping("/promises/{promise-id}/location")
    fun updatePromiseLocation(@PathVariable(value = "promise-id") promiseId: Long, @RequestBody meetPlace: PlaceVo): PromiseDto {
        return promiseRegisterUseCase.updatePromiseMeetPlace(promiseId, meetPlace)
    }

    @Operation(summary = "약속(promise) 제목 수정", description = "약속 제목을 변경합니다.")
    @RequiresMainUser
    @PutMapping("/promises/{promise-id}/titles")
    fun updatePromiseTitle(
        @PathVariable(value = "promise-id") promiseId: Long,
        @RequestParam title: String,
    ): PromiseDto {
        return promiseRegisterUseCase.updatePromiseTitle(promiseId, title)
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
