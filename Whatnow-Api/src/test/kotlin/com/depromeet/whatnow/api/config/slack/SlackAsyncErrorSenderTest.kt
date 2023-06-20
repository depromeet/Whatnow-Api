package com.depromeet.whatnow.api.config.slack

import com.fasterxml.jackson.databind.ObjectMapper
import com.slack.api.model.block.LayoutBlock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.util.ContentCachingRequestWrapper

@ExtendWith(MockitoExtension::class)
class SlackAsyncErrorSenderTest {

    private lateinit var slackAsyncErrorSender: SlackAsyncErrorSender

    @Mock
    private lateinit var slackProvider: SlackErrorNotificationProvider

    @Mock
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        slackAsyncErrorSender = SlackAsyncErrorSender(slackProvider, objectMapper)
    }

    @Test
    fun `슬랙 메시지를 보내야 한다`() {
        // Given
        val request = MockHttpServletRequest() // 적절한 HttpServletRequest 객체를 생성
        val cachingRequest = ContentCachingRequestWrapper(request)

        val userId = 123L

        // When
        slackAsyncErrorSender.execute(cachingRequest, userId)

        // Then
        verify(slackProvider).sendNotification(anyList<LayoutBlock>())
    }

    @Test
    fun `예외 메시지와 함께 슬랙 메시지를 보내야 한다`() {
        // Given
        val e = Exception()
        val userId = 123L

        val request = MockHttpServletRequest() // 적절한 HttpServletRequest 객체를 생성
        val cachingRequest = ContentCachingRequestWrapper(request)

        // When
        slackAsyncErrorSender.execute(cachingRequest, e, userId)

        // Then
        verify(slackProvider).sendNotification(anyList<LayoutBlock>())
    }

    @Test
    fun `method name exception params 를 포함한 슬랙 메시지를 보내야 한다`() {
        // Given
        val methodName = "testMethod"
        val throwable = Throwable()
        val params = arrayOf("param1", 2, true)

        // When
        slackAsyncErrorSender.execute(methodName, throwable, params)

        // Then
        verify(slackProvider).sendNotification(anyList<LayoutBlock>())
    }
}
