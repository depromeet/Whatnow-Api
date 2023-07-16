package com.depromeet.whatnow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.web.filter.ForwardedHeaderFilter

@SpringBootApplication
@EnableAspectJAutoProxy
class WhatnowApiApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<WhatnowApiApplication>(*args)
        }
    }

    @Bean
    fun forwardedHeaderFilter(): ForwardedHeaderFilter {
        return ForwardedHeaderFilter()
    }
}
