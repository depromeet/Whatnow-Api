package com.depromeet.whatnow.api.config.slack

import com.depromeet.whatnow.config.slack.SlackProperties
import com.depromeet.whatnow.config.slack.SlackProperties.SlackSecret
import com.slack.api.Slack
import com.slack.api.webhook.Payload
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.net.UnknownHostException

@Service
class SlackMessageProvider(
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackSecret = slackProperties.webhook
    private fun send(url: String, text: String) {
        val slack = Slack.getInstance()
        val payload = Payload.builder()
            .text(text)
            .username(slackWebHook.userName)
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
