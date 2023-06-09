package com.depromeet.whatnow.config.s3

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.depromeet.whatnow.helper.SpringEnvironmentHelper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.core.env.Environment
import kotlin.test.assertContains

@ExtendWith(MockitoExtension::class)
class S3ServiceTest {
    lateinit var s3Service: S3Service

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

    val springEnvironmentHelper: SpringEnvironmentHelper
        get() {
            val environment = mock(Environment::class.java)
            `when`(environment.activeProfiles).thenReturn(arrayOf("local"))
            return SpringEnvironmentHelper(environment)
        }

    @BeforeEach
    fun setup() {
        s3Service = S3Service(amazonS3, s3Properties, springEnvironmentHelper)
    }

    @Test
    fun `약속 이미지 PresignedUrl을 생성한다`() {
        // given
        val promiseId = 1L
        val imageFIleExtension = ImageFileExtension.JPG

        // when
        val presignedUrl = s3Service.getPresignedUrlForPromise(promiseId, imageFIleExtension)

        // then
        val resultUrl = "https://${s3Properties.s3.bucket}.${s3Properties.s3.endpoint.replace("https://", "")}/local/promise/$promiseId/${presignedUrl.key}.${imageFIleExtension.uploadExtension}"

        assertContains(presignedUrl.url, resultUrl)
    }

    @Test
    fun `유저 프로필 PresignedUrl을 생성한다`() {
        // given
        val userId = 1L
        val imageFIleExtension = ImageFileExtension.JPG

        // when
        val presignedUrl = s3Service.getPresignedUrlForUser(userId, imageFIleExtension)

        // then
        val resultUrl = "https://${s3Properties.s3.bucket}.${s3Properties.s3.endpoint.replace("https://", "")}/local/user/$userId/${presignedUrl.key}.${imageFIleExtension.uploadExtension}"

        assertContains(presignedUrl.url, resultUrl)
    }
}
