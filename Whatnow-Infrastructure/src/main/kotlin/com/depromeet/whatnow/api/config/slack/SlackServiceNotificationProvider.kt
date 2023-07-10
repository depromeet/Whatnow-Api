package com.depromeet.whatnow.api.config.slack

import com.depromeet.whatnow.config.slack.SlackProperties
import com.depromeet.whatnow.config.slack.SlackProperties.SlackSecret
import com.slack.api.model.block.LayoutBlock
import org.springframework.stereotype.Component

@Component
class SlackServiceNotificationProvider(
    val slackHelper: SlackHelper,
    val slackProperties: SlackProperties,
) {
    var slackWebHook: SlackSecret = slackProperties.webhook

    fun sendNotification(layoutBlocks: MutableList<LayoutBlock>) {
        slackHelper.sendNotification(slackWebHook.channelId, slackWebHook.token, slackWebHook.channelId, layoutBlocks)
    }
}
