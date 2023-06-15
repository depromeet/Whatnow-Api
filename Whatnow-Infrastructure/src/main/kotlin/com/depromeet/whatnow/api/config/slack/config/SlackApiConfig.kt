package com.depromeet.whatnow.config.slack.config

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackApiConfig(
    @Value("\${slack.webhook.token}")
    private val token: String,
) {

    @get:Bean
    val client: MethodsClient
        get() {
            val slackClient = Slack.getInstance()
            return slackClient.methods(token)
        }
}
