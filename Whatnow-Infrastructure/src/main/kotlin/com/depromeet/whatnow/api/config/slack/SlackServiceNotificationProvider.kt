package com.depromeet.whatnow.config.slack

import com.slack.api.model.block.LayoutBlock
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SlackServiceNotificationProvider(
    val slackHelper: SlackHelper,
) {
    @Value("\${slack.channel.id}")
    private val CHANNEL_ID: String? = null

    fun sendNotification(layoutBlocks: MutableList<LayoutBlock>) {
        slackHelper.sendNotification(CHANNEL_ID!!, layoutBlocks)
    }
}
