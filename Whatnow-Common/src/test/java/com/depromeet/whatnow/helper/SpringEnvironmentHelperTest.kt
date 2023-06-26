package com.depromeet.whatnow.helper

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.env.Environment

@ExtendWith(MockitoExtension::class)
class SpringEnvironmentHelperTest {
    @Mock
    lateinit var environment: Environment

    @InjectMocks
    lateinit var springEnvironmentHelper: SpringEnvironmentHelper

    @Test
    fun `PROD 환경이라면 isProdProfile 은 true 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("prod")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertTrue(springEnvironmentHelper.isProdProfile)
    }

    @Test
    fun `PROD 환경이 아니라면 isProdProfile 은 false 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("local")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertFalse(springEnvironmentHelper.isProdProfile)
    }

    @Test
    fun `DEV 환경이라면 isDevProfile 은 true 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("dev")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertTrue(springEnvironmentHelper.isDevProfile)
    }

    @Test
    fun `DEV 환경이 아니라면 isDevProfile 은 false 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("local")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertFalse(springEnvironmentHelper.isDevProfile)
    }

    @Test
    fun `LOCAL 환경이라면 isLocalProfile 은 true 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("local")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertTrue(springEnvironmentHelper.isLocalProfile)
    }

    @Test
    fun `LOCAL 환경이 아니라면 isLocalProfile 은 false 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("dev")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertFalse(springEnvironmentHelper.isLocalProfile)
    }

    @Test
    fun `PROD 환경이라면 isProdAndDevProfile 은 true 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("prod")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertTrue(springEnvironmentHelper.isProdAndDevProfile)
    }

    @Test
    fun `DEV 환경이라면 isProdAndDevProfile 은 true 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("dev")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertTrue(springEnvironmentHelper.isProdAndDevProfile)
    }

    @Test
    fun `LOCAL 환경이라면 isProdAndDevProfile 은 false 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("local")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertFalse(springEnvironmentHelper.isProdAndDevProfile)
    }

    @Test
    fun `PROD 환경이라면 getActiveProfile 은 prod 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("prod")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertEquals("prod", springEnvironmentHelper.activeProfile)
    }

    @Test
    fun `DEV 환경이라면 getActiveProfile 은 dev 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("dev")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertEquals("dev", springEnvironmentHelper.activeProfile)
    }

    @Test
    fun `LOCAL 환경이라면 getActiveProfile 은 local 를 반환한다`() {
        // given
        val activeProfiles = arrayOf("local")
        given(environment.activeProfiles).willReturn(activeProfiles)

        // when

        // then
        assertEquals("local", springEnvironmentHelper.activeProfile)
    }
}
