package com.depromeet.whatnow.config.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(
    s3Properties: S3Properties,
) {
    val s3Secret: S3Properties.S3Secret = s3Properties.s3

    @Bean
    fun amazonS3(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(s3Secret.endpoint, s3Secret.region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(s3Secret.accessKey, s3Secret.secretKey)))
            .build()
    }
}
