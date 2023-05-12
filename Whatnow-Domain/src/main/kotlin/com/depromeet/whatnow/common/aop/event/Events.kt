package com.depromeet.whatnow.common.aop.event

import org.springframework.context.ApplicationEventPublisher

class Events {

    companion object {

        @JvmStatic
        val publisherLocal = ThreadLocal<ApplicationEventPublisher>()
        fun raise(event: DomainEvent) {
            publisherLocal.get().publishEvent(event)
        }

        fun setPublisher(publisher: ApplicationEventPublisher) {
            publisherLocal.set(publisher)
        }

        fun reset() {
            publisherLocal.remove()
        }
    }
}
