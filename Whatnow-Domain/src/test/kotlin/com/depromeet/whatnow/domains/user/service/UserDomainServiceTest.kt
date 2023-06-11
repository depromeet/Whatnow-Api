package com.depromeet.whatnow.domains.user.service

import com.depromeet.whatnow.domains.user.adapter.UserAdapter
import com.depromeet.whatnow.domains.user.exception.AlreadySignUpUserException
import com.depromeet.whatnow.domains.user.exception.UserNotFoundException
import com.depromeet.whatnow.helper.oauthInfo_kakao_1_fixture
import com.depromeet.whatnow.helper.user_id_1_fixture
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.willReturn

@ExtendWith(MockitoExtension::class)
class UserDomainServiceTest {

    @Mock
    lateinit var userAdapter: UserAdapter

    private lateinit var userDomainService: UserDomainService

    @BeforeEach
    fun setup() {
        userDomainService = UserDomainService(userAdapter)
    }

    @Test
    fun `유저가 회원가입한 상태면 회원가입 가능 여부가 false 가 되어야한다`() {
        given(userAdapter.queryByOauthInfo(any())).willReturn { user_id_1_fixture() }
        val canRegister = userDomainService.checkUserCanRegister(oauthInfo_kakao_1_fixture())
        assertEquals(canRegister, false)
    }

    @Test
    fun `유저가 회원가입하지 않은 상태면 회원가입 가능 여부가 true 가 되어야한다`() {
        given(userAdapter.queryByOauthInfo(any())).willThrow(UserNotFoundException.EXCEPTION)
        val canRegister = userDomainService.checkUserCanRegister(oauthInfo_kakao_1_fixture())
        assertEquals(canRegister, true)
    }

    @Test
    fun `유저가 회원가입한 상태에서 회원가입을 중복요청하면 이미 회원가입한 에러가 발생해야한다`() {
        given(userAdapter.queryByOauthInfo(any())).willReturn { user_id_1_fixture() }
        assertThrows(AlreadySignUpUserException::class.java) {
            userDomainService.registerUser(
                "username",
                "image",
                true,
                oauthInfo_kakao_1_fixture(),
                "1",
                "fcmToken",
                true,
            )
        }
    }

    @Test
    fun `유저회원가입이 정상적으로 동작해야한다`() {
        given(userAdapter.queryByOauthInfo(any())).willThrow(UserNotFoundException.EXCEPTION)
        given(userAdapter.save(any())).willReturn { user_id_1_fixture() }

        userDomainService.registerUser(
            "유저1",
            "url1",
            true,
            oauthInfo_kakao_1_fixture(),
            "1",
            "fcmToken",
            true,
        )
        assertEquals(user_id_1_fixture().id, 1)
    }
}
