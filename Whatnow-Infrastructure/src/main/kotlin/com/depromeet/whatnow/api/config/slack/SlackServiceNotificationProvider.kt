package com.depromeet.whatnow.config.slack

import com.slack.api.model.block.LayoutBlock
import org.springframework.stereotype.Component

@Component
class SlackServiceNotificationProvider(
    val slackHelper: SlackHelper,
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackProperties.SlackSecret = slackProperties.webhook

    fun sendNotification(layoutBlocks: MutableList<LayoutBlock>) {
        slackHelper.sendNotification(slackWebHook.channelId, layoutBlocks)
    }
}
