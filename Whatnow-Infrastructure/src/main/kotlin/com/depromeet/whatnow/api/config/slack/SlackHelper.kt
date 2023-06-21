package com.depromeet.whatnow.api.config.slack

import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import com.slack.api.methods.MethodsClient
import com.slack.api.methods.SlackApiException
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.slack.api.model.block.LayoutBlock
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SlackHelper(
    val springEnvironmentHelper: SpringEnvironmentHelper,
    val methodsClient: MethodsClient,
) {
    val logger: Logger = LoggerFactory.getLogger(SlackHelper::class.java)
    fun sendNotification(CHANNEL_ID: String, layoutBlocks: List<LayoutBlock>) {
        if (!springEnvironmentHelper.isProdAndDevProfile) {
            return
        }
        val chatPostMessageRequest = ChatPostMessageRequest.builder()
            .channel(CHANNEL_ID)
            .text("")
            .blocks(layoutBlocks)
            .build()
        try {
            methodsClient.chatPostMessage(chatPostMessageRequest)
        } catch (slackApiException: SlackApiException) {
            logger.error(slackApiException.toString())
        }
    }
}
