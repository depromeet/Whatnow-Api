package com.depromeet.whatnow.domains.promiseactive.listener

import com.depromeet.whatnow.common.aop.event.Events
import com.depromeet.whatnow.domains.promise.adaptor.PromiseAdaptor
import com.depromeet.whatnow.domains.promiseactive.repository.PromiseActiveRepository
import com.depromeet.whatnow.domains.promiseuser.adaptor.PromiseUserAdaptor
import com.depromeet.whatnow.events.domainEvent.PromiseTimeEndEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTimeStartEvent
import com.depromeet.whatnow.events.domainEvent.PromiseTrackingTimeEndEvent
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class RedisExpireEventRedisMessageListener(
    val promiseAdapter: PromiseAdaptor,
    val promiseUserAdaptor: PromiseUserAdaptor,
    val promiseActiveRepository: PromiseActiveRepository,
) : MessageListener {
    @Transactional
    override fun onMessage(message: Message, pattern: ByteArray?) {
        val event = message.toString()
        val eventParts = event.split("_")

        if (!event.startsWith("EXPIRE_EVENT_") || eventParts.size < 6) {
            return
        }

        val promiseId = eventParts[5].toLong()
        when (event) {
            "EXPIRE_EVENT_PROMISE_TIME_START_$promiseId" -> handlePromiseTimeStart(promiseId)
            "EXPIRE_EVENT_PROMISE_TIME_END_$promiseId" -> handlePromiseTimeEnd(promiseId)
            "EXPIRE_EVENT_TRACKING_TIME_START_$promiseId" -> handleTrackingTimeEnd(promiseId)
        }
    }

    private fun handlePromiseTimeStart(key: Long) {
        val promiseUsers = promiseUserAdaptor.findByPromiseId(key)
        if (promiseUsers.size < 2) {
            promiseActiveRepository.findById("EXPIRE_EVENT_PROMISE_TIME_END_$key").ifPresent {
                promiseActiveRepository.delete(it)
            }
            promiseActiveRepository.findById("EXPIRE_EVENT_TRACKING_TIME_START_$key").ifPresent {
                promiseActiveRepository.delete(it)
            }
            return
        }
        Events.raise(PromiseTimeStartEvent(key))
    }

    private fun handlePromiseTimeEnd(key: Long) {
        Events.raise(PromiseTimeEndEvent(key))
    }

    private fun handleTrackingTimeEnd(key: Long) {
        Events.raise(PromiseTrackingTimeEndEvent(key))
    }
}
