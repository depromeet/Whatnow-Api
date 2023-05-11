package com.depromeet.whatnow.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(OauthProperties::class)
@Configuration
class EnableConfigProperties
