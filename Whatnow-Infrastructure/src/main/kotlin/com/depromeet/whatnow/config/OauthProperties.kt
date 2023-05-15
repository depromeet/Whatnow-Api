package com.depromeet.whatnow.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "oauth")
@ConstructorBinding
data class OauthProperties(
    val kakao: OAuthSecret,
) {
    data class OAuthSecret(
        val baseUrl: String,
        val clientId: String,
        val clientSecret: String,
        val redirectUrl: String,
        val appId: String,
        val adminKey : String,
    )
}
