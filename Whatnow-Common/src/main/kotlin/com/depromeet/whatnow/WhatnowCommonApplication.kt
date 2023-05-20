package com.depromeet.whatnow

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhatnowCommonApplication

fun main(args: Array<String>) {
    runApplication<WhatnowCommonApplication>(*args)
}
