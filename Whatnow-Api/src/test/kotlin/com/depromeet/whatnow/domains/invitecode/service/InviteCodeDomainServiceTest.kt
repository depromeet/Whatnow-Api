package com.depromeet.whatnow.domains.invitecode.service

import com.depromeet.whatnow.domains.invitecode.adapter.InviteCodeAdapter
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
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
    fun `약속 id로 매번 유니크한 초대 코드를 생성한다`() {
        // given
        val promiseId1 = 1L
        val promiseId2 = 2L
        // when
        val code1 = inviteCodeDomainService.createInviteCode(promiseId1)
        val code2 = inviteCodeDomainService.createInviteCode(promiseId2)
        // then
        print("code1 : $code1, code2 : $code2")
        assertNotEquals(code1, code2)
    }
}
