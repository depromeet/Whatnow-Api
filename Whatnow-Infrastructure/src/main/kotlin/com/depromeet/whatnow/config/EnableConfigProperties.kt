package com.depromeet.whatnow.config

import com.depromeet.whatnow.config.s3.S3Properties
import com.depromeet.whatnow.config.slack.SlackProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@EnableConfigurationProperties(MongoDBProperties::class, NcpProperties::class, SlackProperties::class, OauthProperties::class, S3Properties::class)
@Configuration
class EnableConfigProperties
