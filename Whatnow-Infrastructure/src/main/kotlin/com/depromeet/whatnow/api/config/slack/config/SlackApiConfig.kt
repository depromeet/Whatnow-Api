package com.depromeet.whatnow.api.config.slack.config

import com.depromeet.whatnow.config.slack.SlackProperties
import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackApiConfig(
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackProperties.SlackSecret = slackProperties.webhook

    @get:Bean
    val client: MethodsClient
        get() {
            val slackClient = Slack.getInstance()
            return slackClient.methods(slackWebHook.token)
        }
}
