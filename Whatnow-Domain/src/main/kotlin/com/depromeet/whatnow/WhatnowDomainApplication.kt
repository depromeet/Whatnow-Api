package com.depromeet.whatnow

import com.depromeet.whatnow.config.mongo.MongoConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(MongoConfig::class)
class WhatnowDomainApplication

fun main(args: Array<String>) {
    runApplication<WhatnowDomainApplication>(*args)
}
