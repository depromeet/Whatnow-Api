package com.depromeet.whatnow.api.config.slack

import com.fasterxml.jackson.databind.ObjectMapper
import com.slack.api.model.block.Blocks
import com.slack.api.model.block.Blocks.divider
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.HeaderBlock.HeaderBlockBuilder
import com.slack.api.model.block.LayoutBlock
import com.slack.api.model.block.SectionBlock.SectionBlockBuilder
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.composition.MarkdownTextObject
import com.slack.api.model.block.composition.TextObject
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper

@Component
class SlackAsyncErrorSender(
    val slackProvider: SlackErrorNotificationProvider,
    val objectMapper: ObjectMapper,
) {
    fun execute(cachingRequest: ContentCachingRequestWrapper, userId: Long) {
        val url = cachingRequest.requestURL.toString()
        val method = cachingRequest.method
        val body = objectMapper.readTree(cachingRequest.contentAsByteArray.contentToString())
        val errorUserIP = cachingRequest.remoteAddr
        val layoutBlocks: MutableList<LayoutBlock> = ArrayList()
        layoutBlocks.add(
            Blocks.header { headerBlockBuilder: HeaderBlockBuilder ->
                headerBlockBuilder.text(
                    plainText("Rate Limit Error"),
                )
            },
        )
        layoutBlocks.add(divider())
        val errorUserIdMarkdown = MarkdownTextObject.builder().text("* User Id :*\n$userId").build()
        val errorUserIpMarkdown = MarkdownTextObject.builder().text("* User IP :*\n$errorUserIP").build()
        layoutBlocks.add(
            section { section: SectionBlockBuilder ->
                section.fields(
                    java.util.List.of<TextObject>(
                        errorUserIdMarkdown,
                        errorUserIpMarkdown,
                    ),
                )
            },
        )
        val methodMarkdown = MarkdownTextObject.builder()
            .text("* Request Addr :*\n$method : $url")
            .build()
        val bodyMarkdown = MarkdownTextObject.builder().text("* Request Body :*\n$body").build()
        val fields = java.util.List.of<TextObject>(methodMarkdown, bodyMarkdown)
        layoutBlocks.add(
            section { section: SectionBlockBuilder ->
                section.fields(
                    fields,
                )
            },
        )
        layoutBlocks.add(divider())
        slackProvider.sendNotification(layoutBlocks)
    }

    fun execute(cachingRequest: ContentCachingRequestWrapper, e: Exception, userId: Long) {
        val layoutBlock = mutableListOf<LayoutBlock>()

        val url = cachingRequest.requestURL.toString()
        val method = cachingRequest.method
        val body = objectMapper.readTree(cachingRequest.contentAsByteArray.contentToString())

        val errorMessage = e.message
        val errorStack = slackProvider.getErrorStack(e)
        val errorUserIP = cachingRequest.remoteAddr

        layoutBlock.add(
            Blocks.header { headerBlockBuilder: HeaderBlockBuilder ->
                headerBlockBuilder.text(
                    plainText("Error Detection"),
                )
            },
        )
        layoutBlock.add(divider())

        val errorUserIdMarkdown = MarkdownTextObject.builder()
            .text("* User Id :*\n$userId")
            .build()
        val errorUserIpMarkdown = MarkdownTextObject.builder()
            .text("* User IP :*\n$errorUserIP")
            .build()
        layoutBlock.add(
            section { section ->
                section.fields(listOf(errorUserIdMarkdown, errorUserIpMarkdown))
            },
        )
        val methodMarkdown = MarkdownTextObject.builder()
            .text("* Request Addr :*\n$method : $url")
            .build()
        val bodyMarkdown = MarkdownTextObject.builder().text("* Request Body :*\n$body").build()
        layoutBlock.add(
            section { section ->
                section.fields(listOf(methodMarkdown, bodyMarkdown))
            },
        )

        layoutBlock.add(divider())

        val errorNameMarkdown = MarkdownTextObject.builder().text("* Message :*\n$errorMessage").build()
        val errorStackMarkdown = MarkdownTextObject.builder().text("* Stack Trace :*\n$errorStack").build()
        layoutBlock.add(
            section { section ->
                section.fields(listOf(errorNameMarkdown, errorStackMarkdown))
            },
        )
        slackProvider.sendNotification(layoutBlock)
    }

    fun execute(methodName: String, throwable: Throwable, params: Array<out Any?>) {
        val layoutBlocks = createLayoutBlocks(methodName, throwable, params)
        slackProvider.sendNotification(layoutBlocks)
    }

    private fun createLayoutBlocks(methodName: String, throwable: Throwable, params: Array<out Any?>): List<LayoutBlock> {
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

    private fun getParamsToString(params: Array<out Any?>): String {
        return params.joinToString(separator = ", ")
    }
}
