package com.depromeet.whatnow.api.promiseuser.usecase

import com.depromeet.whatnow.api.promiseuser.dto.PromiseUserDto
import com.depromeet.whatnow.common.vo.CoordinateVo
import com.depromeet.whatnow.domains.progresshistory.adapter.ProgressHistoryAdapter
import com.depromeet.whatnow.domains.progresshistory.domain.ProgressHistory
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress
import com.depromeet.whatnow.domains.progresshistory.domain.PromiseProgress.DEFAULT
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUser
import com.depromeet.whatnow.domains.promiseuser.domain.PromiseUserType
import com.depromeet.whatnow.domains.promiseuser.service.PromiseUserDomainService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PromiseUserReadUseCaseTest {
    @Mock
    private lateinit var promiseUserDomainService: PromiseUserDomainService

    @Mock
    private lateinit var progressHistoryAdapter: ProgressHistoryAdapter

    @Mock
    private lateinit var promiseUserAdaptor: PromiseUserAdaptor

    private lateinit var promiseUserReadUseCase: PromiseUserReadUseCase

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        promiseUserReadUseCase = PromiseUserReadUseCase(promiseUserDomainService, progressHistoryAdapter, promiseUserAdaptor)
    }

    @Test
    fun `PromiseId로_회원을_조회할_수_있다`() {
        val promiseId = 1L
        val location1 = CoordinateVo(100.4, 1.2)
        val location2 = CoordinateVo(123.4, 132.6)
        val promiseUserList = listOf(
            PromiseUser(1L, 1L, location1, PromiseUserType.READY),
            PromiseUser(1L, 2L, location2, PromiseUserType.LATE),
        )
        val progressHistoryList = listOf(
            ProgressHistory(1L, 1L, PromiseProgress.DEFAULT, PromiseProgress.DEFAULT),
            ProgressHistory(1L, 2L, PromiseProgress.BED, PromiseProgress.DEFAULT),
        )
        val expected = listOf(
            PromiseUserDto(1L, 1L, location1, PromiseUserType.READY, PromiseProgress.DEFAULT),
            PromiseUserDto(1L, 2L, location2, PromiseUserType.LATE, PromiseProgress.BED),
        )
        `when`(promiseUserDomainService.findByPromiseId(promiseId)).thenReturn(promiseUserList)
        `when`(progressHistoryAdapter.findByPromiseId(promiseId)).thenReturn(progressHistoryList)
        val result = promiseUserReadUseCase.findByPromiseId(promiseId)

        assertEquals(expected, result)
    }
}
