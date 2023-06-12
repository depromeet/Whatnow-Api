package com.depromeet.whatnow.config.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertContains

@ExtendWith(MockitoExtension::class)
class S3UploadPresignedUrlServiceTest {
    lateinit var s3UploadPresignedUrlService: S3UploadPresignedUrlService

    var s3Properties: S3Properties = S3Properties(
        S3Properties.S3Secret(
            accessKey = "accessKey",
            secretKey = "secretKey",
            region = "ap-northeast-2",
            endpoint = "https://kr.object.ncloudstorage.com",
            bucket = "bucket",
        ),
    )

    var amazonS3: AmazonS3 =
        AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(s3Properties.s3.endpoint, s3Properties.s3.region))
            .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(s3Properties.s3.accessKey, s3Properties.s3.secretKey)))
            .build()

    @BeforeEach
    fun setup() {
        s3UploadPresignedUrlService = S3UploadPresignedUrlService(amazonS3, s3Properties)
    }

    @Test
    fun `PresignedUrl을 생성한다`() {
        // given
        val promiseId = 1L
        val imageFIleExtension = ImageFileExtension.JPG

        // when
        val presignedUrl = s3UploadPresignedUrlService.forPromise(promiseId, imageFIleExtension)

        // then
        val resultUrl = """
            https:
            //${s3Properties.s3.bucket}.${s3Properties.s3.endpoint.replace("https://", "")}
            /promise
            /$promiseId
            /${presignedUrl.key}.${imageFIleExtension.uploadExtension}
            """

        assertContains(presignedUrl.url, resultUrl)
    }
}
