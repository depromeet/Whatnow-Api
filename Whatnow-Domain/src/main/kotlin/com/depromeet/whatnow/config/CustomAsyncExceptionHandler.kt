package com.depromeet.whatnow.config

import com.depromeet.whatnow.api.config.slack.SlackAsyncErrorSender
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.stereotype.Component
import java.lang.reflect.Method
import java.util.logging.Logger

@Component
class CustomAsyncExceptionHandler(
    val slackAsyncErrorSender: SlackAsyncErrorSender,
) : AsyncUncaughtExceptionHandler {

    override fun handleUncaughtException(throwable: Throwable, method: Method, vararg params: Any?) {
        // Loger
        Logger.getLogger(method.name).warning(throwable.message)
        for (param in params) {
            Logger.getLogger(method.name).warning(param.toString())
        }
        // Slack
        slackAsyncErrorSender.execute(method.name, throwable, params)
    }
}
