package com.depromeet.whatnow.domains.invitecode.service

import com.depromeet.whatnow.consts.INVITE_CODE_EXPIRED_TIME
import com.depromeet.whatnow.domains.invitecode.adapter.InviteCodeAdapter
import com.depromeet.whatnow.domains.invitecode.domain.InviteCodeRedisEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertNotEquals

@ExtendWith(MockitoExtension::class)
class InviteCodeDomainServiceTest() {
    @Mock
    private lateinit var inviteCodeAdapter: InviteCodeAdapter

    private lateinit var inviteCodeDomainService: InviteCodeDomainService

    @BeforeEach
    fun setUp() {
        inviteCodeDomainService = InviteCodeDomainService(inviteCodeAdapter)
    }

    @Test
    fun `사전에 저장된 경우 약속 id로 초대 코드를 생성하지 않고 기존 값을 반환한다`() {
        // given
        val promiseId1 = 1L
        val promiseId2 = 2L

        `when`(inviteCodeAdapter.findByPromiseId(promiseId1)).thenReturn(InviteCodeRedisEntity(promiseId1, "code1", INVITE_CODE_EXPIRED_TIME))
        `when`(inviteCodeAdapter.findByPromiseId(promiseId2)).thenReturn(InviteCodeRedisEntity(promiseId2, "code2", INVITE_CODE_EXPIRED_TIME))

        // when
        val code1 = inviteCodeDomainService.upsertInviteCode(promiseId1)
        val code2 = inviteCodeDomainService.upsertInviteCode(promiseId2)

        // then
        assertNotEquals(code1, code2)
    }

    @Test
    fun `저장되지 않은 경우 약속 id로 매번 유니크한 초대 코드를 생성한다`() {
        // given
        val promiseId1 = 1L
        val promiseId2 = 2L

        // Mock the behavior of inviteCodeAdapter.findByPromiseId to use inviteCodeRepository
        `when`(inviteCodeAdapter.findByPromiseId(promiseId1)).thenCallRealMethod()
        `when`(inviteCodeAdapter.findByPromiseId(promiseId2)).thenCallRealMethod()

        // when
        val code1 = inviteCodeDomainService.upsertInviteCode(promiseId1)
        val code2 = inviteCodeDomainService.upsertInviteCode(promiseId2)

        // then
        assertNotEquals(code1, code2)
    }
}
