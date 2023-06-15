package com.depromeet.whatnow.config.slack

import com.slack.api.Slack
import com.slack.api.webhook.Payload
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.net.UnknownHostException

@Service
class SlackMessageProvider(
    @Value("\${slack.webhook.username}")
    val username: String,

    @Value("\${slack.webhook.icon-url}")
    val iconUrl: String,
) {

    private fun send(url: String, text: String) {
        val slack = Slack.getInstance()
        val payload = Payload.builder()
            .text(text)
            .username(username)
            .iconUrl(iconUrl)
            .build()
        try {
            val responseBody = slack.send(url, payload).body
            if (!responseBody.equals("ok")) {
                throw UnknownHostException("Slack Id 가 올바르지 않습니다.")
            }
        } catch (error: UnknownHostException) {
            throw error
        } catch (error: Exception) {
            throw RuntimeException(error)
        }
    }
}
