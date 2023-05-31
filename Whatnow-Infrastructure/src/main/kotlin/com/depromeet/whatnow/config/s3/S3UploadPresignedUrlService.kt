package com.depromeet.whatnow.config.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class S3UploadPresignedUrlService(
    val amazonS3: AmazonS3,
    s3Properties: S3Properties
) {
    val s3Secret: S3Properties.S3Secret = s3Properties.s3

    fun forPromise(promiseId: Long, fileExtension: ImageFileExtension): ImageUrlDto {
        val uuid = UUID.randomUUID().toString()
        var fileName = getForPromiseFimeName(uuid, promiseId, fileExtension)
        val generatePresignedUrlRequest =
            getGeneratePreSignedUrlRequest(s3Secret.bucket, fileName, fileExtension.uploadExtension)

        val generatePresignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
        return ImageUrlDto(generatePresignedUrl.toString(), uuid)
    }

    private fun getForPromiseFimeName(uuid: String, promiseId: Long, fileExtension: ImageFileExtension): String {
        return "promise/" + promiseId + "/" + uuid + "." + fileExtension.uploadExtension
    }

    private fun getGeneratePreSignedUrlRequest(
        bucket: String,
        fileName: String,
        fileExtension: String,
    ): GeneratePresignedUrlRequest {
        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucket, fileName, HttpMethod.PUT)
            .withKey(fileName)
            .withContentType("image/$fileExtension")
            .withExpiration(getPreSignedUrlExpiration())
        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString(),
        )
        return generatePresignedUrlRequest
    }

    private fun getPreSignedUrlExpiration(): Date {
        val expiration = Date()
        var expTimeMillis = expiration.time
        expTimeMillis += 1000 * 60 * 30
        expiration.time = expTimeMillis
        return expiration
    }
}
