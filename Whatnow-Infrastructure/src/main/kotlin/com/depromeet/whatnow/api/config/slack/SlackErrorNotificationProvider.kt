package com.depromeet.whatnow.config.slack

import com.depromeet.whatnow.annotation.Helper
import com.slack.api.model.block.LayoutBlock
import org.springframework.scheduling.annotation.Async
import java.util.Arrays

@Helper
class SlackErrorNotificationProvider(
    val slackHelper: SlackHelper,
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackProperties.SlackSecret = slackProperties.webhook
    val MAX_LENGTH = 500

    fun getErrorStack(throwable: Throwable): String {
        val exceptionAsStrings = Arrays.toString(throwable.stackTrace)
        val cutLength = Math.min(exceptionAsStrings.length, MAX_LENGTH)
        return exceptionAsStrings.substring(0, cutLength)
    }

    @Async
    fun sendNotification(layoutBlocks: List<LayoutBlock>) {
        slackHelper.sendNotification(slackWebHook.channelId, layoutBlocks)
    }
}
