package com.depromeet.whatnow.api.config.slack

import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import com.slack.api.Slack
import com.slack.api.methods.SlackApiException
import com.slack.api.model.block.LayoutBlock
import com.slack.api.webhook.Payload
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SlackHelper(
    val springEnvironmentHelper: SpringEnvironmentHelper,
) {
    val logger: Logger = LoggerFactory.getLogger(SlackHelper::class.java)
    fun sendNotification(url: String, token: String, channelId: String, layoutBlocks: List<LayoutBlock>) {
        if (!springEnvironmentHelper.isProdAndDevProfile) {
//        Local 환경일 경우 적용
//        if (!springEnvironmentHelper.isLocalProfile) {
            return
        }
        val methodsClient = Slack.getInstance()
        val payload = Payload.builder()
            .text("Slack Notification")
            .username("WhatNow-Bot")
            .blocks(layoutBlocks)
            .build()
        try {
            methodsClient.send(url, payload)
        } catch (slackApiException: SlackApiException) {
            logger.error(slackApiException.toString())
        }
    }
}
