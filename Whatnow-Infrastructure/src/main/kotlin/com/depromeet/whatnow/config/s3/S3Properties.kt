package com.depromeet.whatnow.config.s3

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "ncp")
@ConstructorBinding
class S3Properties(
    val s3: S3Secret,
) {
    data class S3Secret(
        val accessKey: String,
        val secretKey: String,
        val region: String,
        val endpoint: String,
    )
}
