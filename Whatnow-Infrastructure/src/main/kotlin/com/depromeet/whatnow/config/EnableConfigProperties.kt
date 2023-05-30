package com.depromeet.whatnow.config

import com.depromeet.whatnow.config.s3.S3Properties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(OauthProperties::class, S3Properties::class)
@Configuration
class EnableConfigProperties
