package com.depromeet.whatnow.events.listener

import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.events.domainEvent.PromiseTimeEndEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTimeStartEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTrackingTimeEndEvent
import org.springframework.data.redis.connection.Message
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RedisExpireEventRedisMessageListener : RedisMessageListener {
    // SETEX TTL_EVENT_PROMISE_TIME_START_15 1 ""
    @Transactional
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val event = message.toString()
        val eventParts = event.split("_")

        if (!event.startsWith("TTL_EVENT_") || eventParts.size < 6) {
            return
        }

        val key = eventParts[5].toLong()
        when (event) {
            "TTL_EVENT_PROMISE_TIME_START_$key" -> handlePromiseTimeStart(key)
            "TTL_EVENT_PROMISE_TIME_END_$key" -> handlePromiseTimeEnd(key)
            "TTL_EVENT_TRACKING_TIME_END_$key" -> handleTrackingTimeEnd(key)
            else -> {
                // TODO EXCEPTION
            }
        }
    }

    private fun handlePromiseTimeStart(key: Long) {
        Events.raise(PromiseTimeStartEvent(key))
    }

    private fun handlePromiseTimeEnd(key: Long) {
        Events.raise(PromiseTimeEndEvent(key))
    }

    private fun handleTrackingTimeEnd(key: Long) {
        Events.raise(PromiseTrackingTimeEndEvent(key))
    }
}
