package com.depromeet.whatnow.api.config.slack

import com.depromeet.whatnow.config.slack.SlackErrorNotificationProvider
import com.slack.api.model.block.Blocks
import com.slack.api.model.block.Blocks.divider
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.composition.MarkdownTextObject
import org.springframework.stereotype.Component

@Component
class SlackAsyncErrorSender(
    val slackProvider: SlackErrorNotificationProvider,
) {
    fun execute(methodName: String, throwable: Throwable, params: Array<Any>) {
        val layoutBlocks = createLayoutBlocks(methodName, throwable, params)
        slackProvider.sendNotification(layoutBlocks)
    }

    private fun createLayoutBlocks(methodName: String, throwable: Throwable, params: Array<Any>): List<LayoutBlock> {
        val layoutBlocks = mutableListOf<LayoutBlock>()

        layoutBlocks.add(
            Blocks.header { headerBlockBuilder ->
                headerBlockBuilder.text(plainText("비동기 에러 알림"))
            },
        )
        layoutBlocks.add(divider())

        val errorUserIdMarkdown = MarkdownTextObject.builder()
            .text("* 메소드 이름 :* $methodName")
            .build()

        val errorUserIpMarkdown = MarkdownTextObject.builder()
            .text("* 요청 파라미터 :* ${getParamsToString(params)}".trimIndent())
            .build()

        layoutBlocks.add(
            section { section ->
                section.fields(listOf(errorUserIdMarkdown, errorUserIpMarkdown))
            },
        )
        layoutBlocks.add(divider())

        val errorStack = slackProvider.getErrorStack(throwable)
        val message = throwable.toString()

        val errorNameMarkdown = MarkdownTextObject.builder()
            .text("* Message :* $message")
            .build()
        val errorStackMarkdown = MarkdownTextObject.builder()
            .text("* Stack Trace :  :* $errorStack")
            .build()

        layoutBlocks.add(
            section { section ->
                section.fields(listOf(errorNameMarkdown, errorStackMarkdown))
            },
        )
        return layoutBlocks
    }

    private fun getParamsToString(params: Array<Any>): String {
        return params.joinToString(separator = ", ")
    }
}
