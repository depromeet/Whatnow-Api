package com.depromeet.whatnow.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "ncp")
@ConstructorBinding
data class NcpProperties(
    val local: NcpSecret,
) {
    data class NcpSecret(
        // access-key -> accessKey
        val accessKey: String,
        val secretKey: String,
        val searchUrl: String,
    )
}
