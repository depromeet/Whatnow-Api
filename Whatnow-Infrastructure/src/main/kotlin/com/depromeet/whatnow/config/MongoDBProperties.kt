package com.depromeet.whatnow.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "data")
@ConstructorBinding
data class MongoDBProperties(
    val mongodb: MongoDB,
) {
    data class MongoDB(
        val url: String,
        val host: String,
        val database: String,
        val username: String,
        val password: String,
    )
}
