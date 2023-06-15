package com.depromeet.whatnow.config.slack

import com.slack.api.model.block.LayoutBlock
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.Arrays

@Component
class SlackErrorNotificationProvider(
    val slackHelper: SlackHelper,
    @Value("\${slack.channel.id}")
    private val CHANNEL_ID: String,
) {
    val MAX_LENGTH = 500

    fun getErrorStack(throwable: Throwable): String {
        val exceptionAsStrings = Arrays.toString(throwable.stackTrace)
        val cutLength = Math.min(exceptionAsStrings.length, MAX_LENGTH)
        return exceptionAsStrings.substring(0, cutLength)
    }

    @Async
    fun sendNotification(layoutBlocks: List<LayoutBlock>) {
        slackHelper.sendNotification(CHANNEL_ID!!, layoutBlocks)
    }
}
