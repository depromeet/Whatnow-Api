package com.depromeet.whatnow.config.slack

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "slack")
@ConstructorBinding
data class SlackProperties(
    val webhook: SlackSecret,
) {
    data class SlackSecret(
        val tokenValue: String,
        val userName: String,
        val iconUrl: String,
        val webhookUrl: String,
        val channelId: String,
    )
}
