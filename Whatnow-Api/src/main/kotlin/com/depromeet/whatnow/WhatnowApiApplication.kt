package com.depromeet.whatnow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.depromeet.whatnow.domains.district"])
class WhatnowApiApplication

fun main(args: Array<String>) {
    runApplication<WhatnowApiApplication>(*args)
}
